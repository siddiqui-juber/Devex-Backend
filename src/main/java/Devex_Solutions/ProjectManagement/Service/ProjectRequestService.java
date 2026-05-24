package Devex_Solutions.ProjectManagement.Service;

import Devex_Solutions.ProjectManagement.DTO.ProjectRequestResponse;
import Devex_Solutions.ProjectManagement.DTO.ProjectRequestSubmitRequest;
import Devex_Solutions.ProjectManagement.Entity.Project;
import Devex_Solutions.ProjectManagement.Entity.ProjectRequestEntity;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.ProjectManagement.Enums.RequestStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRepository;
import Devex_Solutions.ProjectManagement.Repository.ProjectRequestRepository;
import Devex_Solutions.Uploads.Service.FileService;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectRequestService {

    private final ProjectRequestRepository requestRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ProjectRequestResponse toResponse(ProjectRequestEntity entity) {
        return ProjectRequestResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .serviceType(entity.getServiceType())
                .estimatedBudget(entity.getEstimatedBudget())
                .timeline(entity.getTimeline())
                .fileUrl(entity.getFileUrl())
                .status(entity.getStatus())
                .rejectionReason(entity.getRejectionReason())
                .clientName(entity.getClient() != null ? entity.getClient().getFullName() : null)
                .clientEmail(entity.getClient() != null ? entity.getClient().getEmail() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ProjectRequestResponse submitRequest(ProjectRequestSubmitRequest request) {
        User client = getCurrentUser();
        ProjectRequestEntity entity = ProjectRequestEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .serviceType(request.getServiceType())
                .estimatedBudget(request.getEstimatedBudget())
                .timeline(request.getTimeline())
                .status(RequestStatus.PENDING)
                .client(client)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return toResponse(requestRepository.save(entity));
    }

    public List<ProjectRequestResponse> getMyRequests() {
        User client = getCurrentUser();
        return requestRepository.findByClientOrderByCreatedAtDesc(client)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProjectRequestResponse uploadFile(UUID requestId, MultipartFile file) throws IOException {
        User client = getCurrentUser();
        ProjectRequestEntity entity = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!entity.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Access denied");
        }
        String fileName = fileService.uploadFile(file);
        entity.setFileUrl(fileName);
        entity.setUpdatedAt(LocalDateTime.now());
        return toResponse(requestRepository.save(entity));
    }

    public List<ProjectRequestResponse> getAllRequests() {
        return requestRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // APPROVE: also auto-creates a Project in the projects table
    public ProjectRequestResponse approveRequest(UUID requestId) {
        ProjectRequestEntity entity = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        entity.setStatus(RequestStatus.APPROVED);
        entity.setRejectionReason(null);
        entity.setUpdatedAt(LocalDateTime.now());
        requestRepository.save(entity);

        // Auto-create project so it appears in both admin & client project lists
        Project project = Project.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .budget(entity.getEstimatedBudget())
                .status(ProjectStatus.IN_PROGRESS)
                .client(entity.getClient())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        projectRepository.save(project);

        return toResponse(entity);
    }

    public ProjectRequestResponse rejectRequest(UUID requestId, String reason) {
        ProjectRequestEntity entity = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        entity.setStatus(RequestStatus.REJECTED);
        entity.setRejectionReason(reason);
        entity.setUpdatedAt(LocalDateTime.now());
        return toResponse(requestRepository.save(entity));
    }
}

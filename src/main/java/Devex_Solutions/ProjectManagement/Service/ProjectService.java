package Devex_Solutions.ProjectManagement.Service;


import Devex_Solutions.ProjectManagement.DTO.ProjectRequest;
import Devex_Solutions.ProjectManagement.DTO.UpdateProjectStatusRequest;
import Devex_Solutions.ProjectManagement.Entity.Project;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRepository;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    // CREATE PROJECT
    public Project createProject(
            ProjectRequest request
    ) {

        User client = userRepository.findById(
                request.getClientId()
        ).orElseThrow();

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .budget(request.getBudget())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())

                .status(ProjectStatus.PENDING)

                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())

                .client(client)
                .build();

        return projectRepository.save(project);
    }

    //GET ALL PROJECT
    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }

    public Project getProjectById(UUID id) {

        return projectRepository.findById(id)
                .orElseThrow();
    }

    //UPDATE PROJECT
    public Project updateProject(
            UUID id,
            ProjectRequest request
    ) {

        Project project = projectRepository.findById(id)
                .orElseThrow();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setBudget(request.getBudget());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        project.setUpdatedAt(LocalDateTime.now());

        return projectRepository.save(project);
    }

    // DELETE PROJECT
    public void deleteProject(UUID id) {

        projectRepository.deleteById(id);
    }

    //UPDATE PROJECT STATUS
    public Project updateProjectStatus(
            UUID projectId,
            UpdateProjectStatusRequest request
    ) {

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow();

        project.setStatus(request.getStatus());

        project.setUpdatedAt(LocalDateTime.now());

        return projectRepository.save(project);
    }
}

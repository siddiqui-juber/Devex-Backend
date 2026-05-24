package Devex_Solutions.ProjectManagement.Controller;

import Devex_Solutions.ProjectManagement.DTO.ProjectRequestResponse;
import Devex_Solutions.ProjectManagement.DTO.ProjectRequestSubmitRequest;
import Devex_Solutions.ProjectManagement.DTO.RejectRequestDTO;
import Devex_Solutions.ProjectManagement.Service.ProjectRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Project Request APIs")
public class ProjectRequestController {

    private final ProjectRequestService requestService;

    // ==================== CLIENT ENDPOINTS ====================

    // POST /api/v1/client/requests - Submit project request
    @PostMapping("/api/v1/client/requests")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Submit a new project request")
    public ProjectRequestResponse submitRequest(
            @RequestBody ProjectRequestSubmitRequest request
    ) {
        return requestService.submitRequest(request);
    }

    // GET /api/v1/client/requests - Get my requests
    @GetMapping("/api/v1/client/requests")
    @Operation(summary = "Get my project requests")
    public List<ProjectRequestResponse> getMyRequests() {
        return requestService.getMyRequests();
    }

    // POST /api/v1/client/requests/{id}/upload - Upload file to request
    @PostMapping(
            value = "/api/v1/client/requests/{id}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Upload file to project request")
    public ProjectRequestResponse uploadFile(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return requestService.uploadFile(id, file);
    }

    // ==================== ADMIN ENDPOINTS ====================

    // GET /api/v1/admin/requests - Get all requests
    @GetMapping("/api/v1/admin/requests")
    @Operation(summary = "Get all client project requests")
    public List<ProjectRequestResponse> getAllRequests() {
        return requestService.getAllRequests();
    }

    // PATCH /api/v1/admin/requests/{id}/approve - Approve request
    @PatchMapping("/api/v1/admin/requests/{id}/approve")
    @Operation(summary = "Approve a project request")
    public ProjectRequestResponse approveRequest(
            @PathVariable UUID id
    ) {
        return requestService.approveRequest(id);
    }

    // PATCH /api/v1/admin/requests/{id}/reject - Reject request
    @PatchMapping("/api/v1/admin/requests/{id}/reject")
    @Operation(summary = "Reject a project request")
    public ProjectRequestResponse rejectRequest(
            @PathVariable UUID id,
            @RequestBody RejectRequestDTO body
    ) {
        return requestService.rejectRequest(id, body.getReason());
    }
}

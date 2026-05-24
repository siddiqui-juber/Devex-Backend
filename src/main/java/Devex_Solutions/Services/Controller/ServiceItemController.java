package Devex_Solutions.Services.Controller;

import Devex_Solutions.Services.DTO.ServiceItemRequest;
import Devex_Solutions.Services.DTO.ServiceItemResponse;
import Devex_Solutions.Services.Service.ServiceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Service Management APIs")
public class ServiceItemController {

    private final ServiceItemService serviceItemService;

    // CLIENT: Get active services
    @GetMapping("/api/v1/client/services")
    @Operation(summary = "Get active services for clients")
    public List<ServiceItemResponse> getActiveServices() {
        return serviceItemService.getActiveServices();
    }

    // ADMIN: Get all services
    @GetMapping("/api/v1/admin/services")
    @Operation(summary = "Get all services (admin)")
    public List<ServiceItemResponse> getAllServices() {
        return serviceItemService.getAllServices();
    }

    // ADMIN: Create service
    @PostMapping("/api/v1/admin/services")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a service")
    public ServiceItemResponse createService(@RequestBody ServiceItemRequest request) {
        return serviceItemService.createService(request);
    }

    // ADMIN: Update service
    @PutMapping("/api/v1/admin/services/{id}")
    @Operation(summary = "Update a service")
    public ServiceItemResponse updateService(
            @PathVariable UUID id,
            @RequestBody ServiceItemRequest request
    ) {
        return serviceItemService.updateService(id, request);
    }

    // ADMIN: Delete service
    @DeleteMapping("/api/v1/admin/services/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a service")
    public void deleteService(@PathVariable UUID id) {
        serviceItemService.deleteService(id);
    }

    // ADMIN: Toggle active status
    @PatchMapping("/api/v1/admin/services/{id}/toggle")
    @Operation(summary = "Toggle service active status")
    public ServiceItemResponse toggleActive(@PathVariable UUID id) {
        return serviceItemService.toggleActive(id);
    }
}

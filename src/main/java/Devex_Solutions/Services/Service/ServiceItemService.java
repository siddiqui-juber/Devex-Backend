package Devex_Solutions.Services.Service;

import Devex_Solutions.Services.DTO.ServiceItemRequest;
import Devex_Solutions.Services.DTO.ServiceItemResponse;
import Devex_Solutions.Services.Entity.ServiceItem;
import Devex_Solutions.Services.Repository.ServiceItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceItemService {

    private final ServiceItemRepository repository;

    private ServiceItemResponse toResponse(ServiceItem item) {
        return ServiceItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .icon(item.getIcon())
                .color(item.getColor())
                .features(item.getFeatures())
                .active(item.isActive())
                .createdAt(item.getCreatedAt())
                .build();
    }

    // CLIENT: Get active services only
    public List<ServiceItemResponse> getActiveServices() {
        return repository.findByActiveTrueOrderByCreatedAtAsc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ADMIN: Get all services (active + inactive)
    public List<ServiceItemResponse> getAllServices() {
        return repository.findAllByOrderByCreatedAtAsc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ADMIN: Create service
    public ServiceItemResponse createService(ServiceItemRequest request) {
        ServiceItem item = ServiceItem.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .icon(request.getIcon())
                .color(request.getColor())
                .features(request.getFeatures())
                .active(request.isActive())
                .createdAt(LocalDateTime.now())
                .build();
        return toResponse(repository.save(item));
    }

    // ADMIN: Update service
    public ServiceItemResponse updateService(UUID id, ServiceItemRequest request) {
        ServiceItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setIcon(request.getIcon());
        item.setColor(request.getColor());
        item.setFeatures(request.getFeatures());
        item.setActive(request.isActive());
        return toResponse(repository.save(item));
    }

    // ADMIN: Delete service
    public void deleteService(UUID id) {
        repository.deleteById(id);
    }

    // ADMIN: Toggle active/inactive
    public ServiceItemResponse toggleActive(UUID id) {
        ServiceItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        item.setActive(!item.isActive());
        return toResponse(repository.save(item));
    }
}

package Devex_Solutions.Services.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemResponse {
    private UUID id;
    private String title;
    private String description;
    private String icon;
    private String color;
    private String features;
    private boolean active;
    private LocalDateTime createdAt;
}

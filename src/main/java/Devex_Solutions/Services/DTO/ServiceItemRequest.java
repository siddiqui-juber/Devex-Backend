package Devex_Solutions.Services.DTO;

import lombok.Data;

@Data
public class ServiceItemRequest {
    private String title;
    private String description;
    private String icon;
    private String color;
    private String features;
    private boolean active;
}

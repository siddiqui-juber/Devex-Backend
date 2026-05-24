package Devex_Solutions.Services.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

    private String color;

    // Comma-separated list of features
    @Column(columnDefinition = "TEXT")
    private String features;

    private boolean active;

    private LocalDateTime createdAt;
}

package Devex_Solutions.ProjectManagement.Entity;

import Devex_Solutions.ProjectManagement.Enums.RequestStatus;
import Devex_Solutions.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String serviceType;

    private BigDecimal estimatedBudget;

    private String timeline;

    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

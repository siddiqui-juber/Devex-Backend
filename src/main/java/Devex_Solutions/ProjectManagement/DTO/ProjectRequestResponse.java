package Devex_Solutions.ProjectManagement.DTO;

import Devex_Solutions.ProjectManagement.Enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestResponse {

    private UUID id;

    private String title;

    private String description;

    private String serviceType;

    private BigDecimal estimatedBudget;

    private String timeline;

    private String fileUrl;

    private RequestStatus status;

    private String rejectionReason;

    private String clientName;

    private String clientEmail;

    private LocalDateTime createdAt;
}

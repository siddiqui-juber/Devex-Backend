package Devex_Solutions.ProjectManagement.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProjectRequest {

    private String title;

    private String description;

    private BigDecimal budget;

    private LocalDate startDate;

    private LocalDate endDate;

    private UUID clientId;
}
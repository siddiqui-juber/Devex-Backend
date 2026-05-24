package Devex_Solutions.ProjectManagement.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectRequestSubmitRequest {

    private String title;

    private String description;

    private String serviceType;

    private BigDecimal estimatedBudget;

    private String timeline;
}

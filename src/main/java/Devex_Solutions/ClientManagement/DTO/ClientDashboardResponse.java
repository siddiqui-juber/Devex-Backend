package Devex_Solutions.ClientManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDashboardResponse {

    private long totalProjects;

    private long completedProjects;

    private long inProgressProjects;

    private long pendingProjects;
}

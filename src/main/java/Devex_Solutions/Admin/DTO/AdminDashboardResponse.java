package Devex_Solutions.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalClients;

    private long totalProjects;

    private long completedProjects;

    private long pendingProjects;

    private long inProgressProjects;
}
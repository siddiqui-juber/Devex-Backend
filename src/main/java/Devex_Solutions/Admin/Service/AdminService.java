package Devex_Solutions.Admin.Service;


import Devex_Solutions.Admin.DTO.AdminDashboardResponse;
import Devex_Solutions.Admin.DTO.ClientResponse;
import Devex_Solutions.Authentication.Enum.Role;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRepository;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    public AdminDashboardResponse getDashboard() {

        long totalClients =
                userRepository.countByRole(Role.CLIENT);

        long totalProjects =
                projectRepository.count();

        long completedProjects =
                projectRepository.countByStatus(
                        ProjectStatus.COMPLETED
                );

        long pendingProjects =
                projectRepository.countByStatus(
                        ProjectStatus.PENDING
                );

        long inProgressProjects =
                projectRepository.countByStatus(
                        ProjectStatus.IN_PROGRESS
                );

        return AdminDashboardResponse.builder()
                .totalClients(totalClients)
                .totalProjects(totalProjects)
                .completedProjects(completedProjects)
                .pendingProjects(pendingProjects)
                .inProgressProjects(inProgressProjects)
                .build();
    }

    //GET ALL CLIENTS
    public List<ClientResponse> getAllClients() {

        List<User> clients =
                userRepository.findByRole(Role.CLIENT);

        return clients.stream()
                .map(client -> ClientResponse.builder()
                        .id(client.getId())
                        .fullName(client.getFullName())
                        .email(client.getEmail())
                        .phone(client.getPhone())
                        .build())
                .toList();
    }
}

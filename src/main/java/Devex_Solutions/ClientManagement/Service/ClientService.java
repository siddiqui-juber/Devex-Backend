package Devex_Solutions.ClientManagement.Service;


import Devex_Solutions.ClientManagement.DTO.ClientDashboardResponse;
import Devex_Solutions.ClientManagement.DTO.ClientProfileResponse;
import Devex_Solutions.ProjectManagement.Entity.Project;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRepository;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    // GET CURRENT CLIENT
    private User getCurrentClient() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow();
    }

    //GET PROFILE
    public ClientProfileResponse getProfile() {

        User user = getCurrentClient();

        return ClientProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }


//GET PROJECTS
    public List<Project> getProjects() {

        User user = getCurrentClient();

        return projectRepository.findByClient(user);
    }

    //GET DASHBOARD
    public ClientDashboardResponse getDashboard() {

        User user = getCurrentClient();

        long totalProjects =
                projectRepository.countByClient(user);

        long completedProjects =
                projectRepository.countByClientAndStatus(
                        user,
                        ProjectStatus.COMPLETED
                );

        long inProgressProjects =
                projectRepository.countByClientAndStatus(
                        user,
                        ProjectStatus.IN_PROGRESS
                );

        long pendingProjects =
                projectRepository.countByClientAndStatus(
                        user,
                        ProjectStatus.PENDING
                );

        return ClientDashboardResponse.builder()
                .totalProjects(totalProjects)
                .completedProjects(completedProjects)
                .inProgressProjects(inProgressProjects)
                .pendingProjects(pendingProjects)
                .build();
    }
}

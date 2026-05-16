package Devex_Solutions.Admin.Service;

import Devex_Solutions.Admin.DTO.AdminAnalyticsResponse;
import Devex_Solutions.Authentication.Enum.Role;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRepository;
import Devex_Solutions.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAnalyticsService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    public AdminAnalyticsResponse getAnalytics() {

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

        double completionRate = 0;

        if (totalProjects > 0) {

            completionRate =
                    ((double) completedProjects
                            / totalProjects) * 100;
        }

        return AdminAnalyticsResponse.builder()
                .totalClients(totalClients)
                .totalProjects(totalProjects)
                .completedProjects(completedProjects)
                .pendingProjects(pendingProjects)
                .inProgressProjects(inProgressProjects)
                .completionRate(completionRate)
                .build();
    }
}
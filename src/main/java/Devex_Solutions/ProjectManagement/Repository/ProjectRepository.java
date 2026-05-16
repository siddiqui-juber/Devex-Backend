package Devex_Solutions.ProjectManagement.Repository;


import Devex_Solutions.ProjectManagement.Entity.Project;
import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import Devex_Solutions.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByClient(User client);

    long countByClient(User client);

    long countByClientAndStatus(
            User client,
            ProjectStatus status
    );
    long countByStatus(ProjectStatus status);
}
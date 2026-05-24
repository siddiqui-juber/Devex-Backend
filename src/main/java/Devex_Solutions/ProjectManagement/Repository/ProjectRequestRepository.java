package Devex_Solutions.ProjectManagement.Repository;

import Devex_Solutions.ProjectManagement.Entity.ProjectRequestEntity;
import Devex_Solutions.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, UUID> {

    List<ProjectRequestEntity> findByClientOrderByCreatedAtDesc(User client);

    List<ProjectRequestEntity> findAllByOrderByCreatedAtDesc();
}

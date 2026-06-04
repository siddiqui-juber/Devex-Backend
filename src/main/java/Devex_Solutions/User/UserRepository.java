package Devex_Solutions.User;

import Devex_Solutions.Authentication.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRole(Role role);
    List<User> findByRole(Role role);

    Optional<User> findByResetToken(String resetToken);
}

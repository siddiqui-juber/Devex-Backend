package Devex_Solutions.Support.Repository;

import Devex_Solutions.Support.Entity.SupportTicket;
import Devex_Solutions.Support.Enums.TicketStatus;
import Devex_Solutions.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {

    List<SupportTicket> findByClientOrderByCreatedAtDesc(User client);

    List<SupportTicket> findAllByOrderByCreatedAtDesc();

    long countByStatus(TicketStatus status);
}

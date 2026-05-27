package Devex_Solutions.Support.Service;

import Devex_Solutions.Support.DTO.SupportTicketRequest;
import Devex_Solutions.Support.DTO.SupportTicketResponse;
import Devex_Solutions.Support.Entity.SupportTicket;
import Devex_Solutions.Support.Enums.TicketStatus;
import Devex_Solutions.Support.Repository.SupportTicketRepository;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupportService {

    private final SupportTicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public SupportService(SupportTicketRepository ticketRepository, UserRepository userRepository,EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.emailService =  emailService;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private SupportTicketResponse toResponse(SupportTicket ticket) {
        return SupportTicketResponse.builder()
                .id(ticket.getId())
                .subject(ticket.getSubject())
                .message(ticket.getMessage())
                .category(ticket.getCategory())
                .status(ticket.getStatus())
                .adminReply(ticket.getAdminReply())
                .clientName(ticket.getClient() != null ? ticket.getClient().getFullName() : null)
                .clientEmail(ticket.getClient() != null ? ticket.getClient().getEmail() : null)
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

    // CLIENT: Create support ticket
    public SupportTicketResponse createTicket(
            SupportTicketRequest request
    ) {

        User client = getCurrentUser();

        SupportTicket ticket = SupportTicket.builder()
                .subject(request.getSubject())
                .message(request.getMessage())
                .category(request.getCategory())
                .status(TicketStatus.OPEN)
                .client(client)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        SupportTicket savedTicket =
                ticketRepository.save(ticket);

        // SEND EMAIL
        emailService.sendSupportNotification(
                savedTicket
        );

        return toResponse(savedTicket);
    }

    // CLIENT: Get my tickets
    public List<SupportTicketResponse> getMyTickets() {
        User client = getCurrentUser();
        return ticketRepository.findByClientOrderByCreatedAtDesc(client)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ADMIN: Get all tickets
    public List<SupportTicketResponse> getAllTickets() {
        return ticketRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ADMIN: Reply and close ticket
    public SupportTicketResponse replyAndClose(UUID ticketId, String reply) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setAdminReply(reply);
        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setUpdatedAt(LocalDateTime.now());

        SupportTicket updatedTicket =
                ticketRepository.save(ticket);

// SEND CLIENT EMAIL
        emailService.sendSupportReplyToClient(
                updatedTicket
        );

        return toResponse(updatedTicket);
    }

    // ADMIN: Mark as in review
    public SupportTicketResponse markInReview(UUID ticketId) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(TicketStatus.IN_REVIEW);
        ticket.setUpdatedAt(LocalDateTime.now());
        return toResponse(ticketRepository.save(ticket));
    }

}

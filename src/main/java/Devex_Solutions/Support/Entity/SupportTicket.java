package Devex_Solutions.Support.Entity;

import Devex_Solutions.Support.Enums.TicketStatus;
import Devex_Solutions.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String category;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TicketStatus status = TicketStatus.OPEN;

    @Column(columnDefinition = "TEXT")
    private String adminReply;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

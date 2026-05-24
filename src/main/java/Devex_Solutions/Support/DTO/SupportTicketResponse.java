package Devex_Solutions.Support.DTO;

import Devex_Solutions.Support.Enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketResponse {

    private UUID id;

    private String subject;

    private String message;

    private String category;

    private TicketStatus status;

    private String adminReply;

    private String clientName;

    private String clientEmail;

    private LocalDateTime createdAt;
}

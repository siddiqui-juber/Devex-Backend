package Devex_Solutions.Support.Controller;

import Devex_Solutions.Support.DTO.AdminReplyDTO;
import Devex_Solutions.Support.DTO.SupportTicketRequest;
import Devex_Solutions.Support.DTO.SupportTicketResponse;
import Devex_Solutions.Support.Service.EmailService;
import Devex_Solutions.Support.Service.SupportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1")
@Tag(name = "Support APIs")

public class SupportController {

    private final SupportService supportService;
    private final EmailService emailService;

    // ==================== CLIENT ====================

    @PostMapping("/client/support")
    public SupportTicketResponse createTicket(
            @RequestBody SupportTicketRequest request
    ) {
        return supportService.createTicket(request);
    }

    @GetMapping("/client/support")
    public List<SupportTicketResponse> getMyTickets() {
        return supportService.getMyTickets();
    }

    // ==================== ADMIN ====================

    @GetMapping("/admin/support")
    public List<SupportTicketResponse> getAllTickets() {
        return supportService.getAllTickets();
    }

    @PatchMapping("/admin/support/{id}/reply")
    public SupportTicketResponse replyAndClose(
            @PathVariable UUID id,
            @RequestBody AdminReplyDTO body
    ) {
        return supportService.replyAndClose(
                id,
                body.getReply()
        );
    }

    @PatchMapping("/admin/support/{id}/review")
    public SupportTicketResponse markInReview(
            @PathVariable UUID id
    ) {
        return supportService.markInReview(id);

    }

}

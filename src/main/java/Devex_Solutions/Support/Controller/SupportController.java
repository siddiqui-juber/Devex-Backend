package Devex_Solutions.Support.Controller;

import Devex_Solutions.Support.DTO.AdminReplyDTO;
import Devex_Solutions.Support.DTO.SupportTicketRequest;
import Devex_Solutions.Support.DTO.SupportTicketResponse;
import Devex_Solutions.Support.Service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Support APIs")
public class SupportController {

    private final SupportService supportService;

    // ==================== CLIENT ENDPOINTS ====================

    // POST /api/v1/client/support - Create ticket
    @PostMapping("/api/v1/client/support")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a support ticket")
    public SupportTicketResponse createTicket(
            @RequestBody SupportTicketRequest request
    ) {
        return supportService.createTicket(request);
    }

    // GET /api/v1/client/support - Get my tickets
    @GetMapping("/api/v1/client/support")
    @Operation(summary = "Get my support tickets")
    public List<SupportTicketResponse> getMyTickets() {
        return supportService.getMyTickets();
    }

    // ==================== ADMIN ENDPOINTS ====================

    // GET /api/v1/admin/support - Get all tickets
    @GetMapping("/api/v1/admin/support")
    @Operation(summary = "Get all support tickets")
    public List<SupportTicketResponse> getAllTickets() {
        return supportService.getAllTickets();
    }

    // PATCH /api/v1/admin/support/{id}/reply - Reply & close
    @PatchMapping("/api/v1/admin/support/{id}/reply")
    @Operation(summary = "Reply to support ticket and close it")
    public SupportTicketResponse replyAndClose(
            @PathVariable UUID id,
            @RequestBody AdminReplyDTO body
    ) {
        return supportService.replyAndClose(id, body.getReply());
    }

    // PATCH /api/v1/admin/support/{id}/review - Mark in review
    @PatchMapping("/api/v1/admin/support/{id}/review")
    @Operation(summary = "Mark support ticket as in review")
    public SupportTicketResponse markInReview(
            @PathVariable UUID id
    ) {
        return supportService.markInReview(id);
    }
}

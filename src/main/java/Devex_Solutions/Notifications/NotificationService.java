package Devex_Solutions.Notifications;


import Devex_Solutions.ProjectManagement.Enums.RequestStatus;
import Devex_Solutions.ProjectManagement.Repository.ProjectRequestRepository;
import Devex_Solutions.Support.Enums.TicketStatus;
import Devex_Solutions.Support.Repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class NotificationService {

    private final ProjectRequestRepository
            requestRepository;

    private final SupportTicketRepository
            supportTicketRepository;

    public NotificationCountResponse
    getCounts() {

        return NotificationCountResponse
                .builder()

                .pendingRequests(
                        requestRepository
                                .countByStatus(
                                        RequestStatus.PENDING
                                )
                )

                .openTickets(
                        supportTicketRepository
                                .countByStatus(
                                        TicketStatus.OPEN
                                )
                )

                .build();
    }
}
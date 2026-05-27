package Devex_Solutions.Notifications;



import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class NotificationCountResponse {

    private long pendingRequests;

    private long openTickets;
}

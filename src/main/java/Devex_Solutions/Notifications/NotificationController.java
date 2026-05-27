package Devex_Solutions.Notifications;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notifications")
@CrossOrigin("*")

public class NotificationController {

    private final NotificationService
            notificationService;

    @GetMapping("/count")
    public NotificationCountResponse
    getCounts() {

        return notificationService
                .getCounts();
    }
}

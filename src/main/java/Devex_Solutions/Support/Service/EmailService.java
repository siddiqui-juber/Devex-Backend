package Devex_Solutions.Support.Service;


import Devex_Solutions.ProjectManagement.Entity.ProjectRequestEntity;
import Devex_Solutions.Support.Entity.SupportTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSupportNotification(
            SupportTicket ticket
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo("genxcrafthq@gmail.com");

        mail.setSubject(
                "New Support Ticket - "
                        + ticket.getSubject()
        );

        mail.setText(
                "A new support ticket was created.\n\n"

                        + "Client: "
                        + ticket.getClient().getFullName()

                        + "\nEmail: "
                        + ticket.getClient().getEmail()

                        + "\nCategory: "
                        + ticket.getCategory()

                        + "\n\nMessage:\n"
                        + ticket.getMessage()
        );

        mailSender.send(mail);
    }
    public void sendRequestNotification(
            ProjectRequestEntity request
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo("genxcrafthq@gmail.com");

        mail.setSubject(
                "🚀 New Project Request"
        );

        mail.setText(

                "A new project request was submitted.\n\n"

                        + "Client: "
                        + request.getClient().getFullName()

                        + "\nEmail: "
                        + request.getClient().getEmail()

                        + "\nService Type: "
                        + request.getServiceType()

                        + "\nBudget: "
                        + request.getEstimatedBudget()

                        + "\nTimeline: "
                        + request.getTimeline()

                        + "\n\nTitle:\n"
                        + request.getTitle()

                        + "\n\nDescription:\n"
                        + request.getDescription()
        );

        mailSender.send(mail);

        System.out.println(
                "REQUEST EMAIL SENT"
        );
    }

    public void sendSupportReplyToClient(
            SupportTicket ticket
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(
                ticket.getClient().getEmail()
        );

        mail.setSubject(
                "✅ Your Support Ticket Has Been Updated"
        );

        mail.setText(

                "Hello "
                        + ticket.getClient().getFullName()

                        + ",\n\n"

                        + "Your support ticket has received a reply.\n\n"

                        + "Subject: "
                        + ticket.getSubject()

                        + "\n\nReply:\n"
                        + ticket.getAdminReply()

                        + "\n\nStatus: "
                        + ticket.getStatus()

                        + "\n\n— DEVEX Support Team"
        );

        mailSender.send(mail);

        System.out.println(
                "CLIENT SUPPORT REPLY EMAIL SENT"
        );
    }
    public void sendProjectApprovedEmail(
            ProjectRequestEntity request
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(
                request.getClient().getEmail()
        );

        mail.setSubject(
                "🎉 Project Request Approved"
        );

        mail.setText(

                "Hello "
                        + request.getClient().getFullName()

                        + ",\n\n"

                        + "Great news! Your project request has been approved.\n\n"

                        + "Project: "
                        + request.getTitle()

                        + "\nService: "
                        + request.getServiceType()

                        + "\nBudget: "
                        + request.getEstimatedBudget()

                        + "\nTimeline: "
                        + request.getTimeline()

                        + "\n\nOur team will contact you soon."

                        + "\n\n— DEVEX Team"
        );

        mailSender.send(mail);

        System.out.println(
                "PROJECT APPROVED EMAIL SENT"
        );
    }
    public void sendProjectRejectedEmail(
            ProjectRequestEntity request
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(
                request.getClient().getEmail()
        );

        mail.setSubject(
                "❌ Project Request Update"
        );

        mail.setText(

                "Hello "
                        + request.getClient().getFullName()

                        + ",\n\n"

                        + "Your project request could not be approved at this time.\n\n"

                        + "Project: "
                        + request.getTitle()

                        + "\n\nReason:\n"
                        + request.getRejectionReason()

                        + "\n\nYou may submit a revised request anytime."

                        + "\n\n— DEVEX Team"
        );

        mailSender.send(mail);

        System.out.println(
                "PROJECT REJECTED EMAIL SENT"
        );
    }


}

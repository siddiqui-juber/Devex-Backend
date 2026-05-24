package Devex_Solutions.Support.DTO;

import lombok.Data;

@Data
public class SupportTicketRequest {

    private String subject;

    private String message;

    private String category;
}

package Devex_Solutions.Authentication.DTO;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}


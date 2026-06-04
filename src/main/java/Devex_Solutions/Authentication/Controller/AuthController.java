package Devex_Solutions.Authentication.Controller;

import Devex_Solutions.Authentication.DTO.*;
import Devex_Solutions.Authentication.Service.AuthService;
import Devex_Solutions.Security.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AuthController {

    private final AuthService authService;

    // REGISTER
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new client")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    // LOGIN
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    // LOGOUT
    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    public ApiSuccessResponse<String> logout() {
        return authService.logout();
    }

    // FORGOT PASSWORD
    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset email")
    public ApiSuccessResponse<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        return authService.forgotPassword(request);
    }

    // RESET PASSWORD
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password using token")
    public ApiSuccessResponse<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        return authService.resetPassword(request);
    }
}
package Devex_Solutions.Authentication.Service;

import Devex_Solutions.Authentication.DTO.*;
import Devex_Solutions.Authentication.Enum.Role;
import Devex_Solutions.Security.ApiSuccessResponse;
import Devex_Solutions.Security.JwtService;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JavaMailSender mailSender;

    //REGISTER
    public AuthResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .phone(request.getPhone())
                .role(Role.CLIENT)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return new AuthResponse(token, user.getRole().name());
    }

    //LOGIN
    public AuthResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return new AuthResponse(token, user.getRole().name());
    }

    //LOGOUT
    public ApiSuccessResponse<String> logout() {

        return ApiSuccessResponse.<String>builder()
                .success(true)
                .message("Logout successful")
                .data(null)
                .build();
    }

    // FORGOT PASSWORD
    public ApiSuccessResponse<String> forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No account found with this email address."));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        String resetLink = "https://devex-mauve.vercel.app/reset-password?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("thedevexhq@gmail.com");
        mail.setSubject("Devex — Reset Your Password");
        mail.setText(
                "Hi " + user.getFullName() + ",\n\n" +
                "You requested a password reset for your Devex account.\n\n" +
                "Click the link below to set a new password (valid for 1 hour):\n\n" +
                resetLink + "\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "— The Devex Team"
        );
        mailSender.send(mail);

        return ApiSuccessResponse.<String>builder()
                .success(true)
                .message("Password reset link sent to " + user.getEmail())
                .data(null)
                .build();
    }

    // RESET PASSWORD
    public ApiSuccessResponse<String> resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token."));

        if (user.getResetTokenExpiry() == null ||
                LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            throw new RuntimeException("Reset token has expired. Please request a new one.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ApiSuccessResponse.<String>builder()
                .success(true)
                .message("Password updated successfully. You can now log in.")
                .data(null)
                .build();
    }
}
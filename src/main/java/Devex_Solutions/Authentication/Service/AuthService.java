package Devex_Solutions.Authentication.Service;

import Devex_Solutions.Authentication.DTO.AuthResponse;
import Devex_Solutions.Authentication.DTO.LoginRequest;
import Devex_Solutions.Authentication.DTO.RegisterRequest;
import Devex_Solutions.Authentication.Enum.Role;
import Devex_Solutions.Security.ApiSuccessResponse;
import Devex_Solutions.Security.JwtService;
import Devex_Solutions.User.User;
import Devex_Solutions.User.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

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
        // USER
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .phone(request.getPhone())

                // ALWAYS CLIENT
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
}
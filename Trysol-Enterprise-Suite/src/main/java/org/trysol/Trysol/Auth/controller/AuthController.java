package org.trysol.Trysol.Auth.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.trysol.Trysol.Auth.Dto.*;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.entity.User;
import org.trysol.Trysol.Auth.exception.ApiException;
import org.trysol.Trysol.Auth.service.EmailService;
import org.trysol.Trysol.Auth.service.UserService;
import org.trysol.Trysol.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final  UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );
        SecurityContextHolder.getContext().setAuthentication(auth);
       //String token = jwtUtil.generateToken(request.getUsernameOrEmail());
        User user = userRepository.findByUsernameOrEmail(
                request.getUsernameOrEmail(),
                request.getUsernameOrEmail()
        ).orElseThrow(() -> new RuntimeException("User not found"));

        // Generate token including role
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().getName()
        );
//        String username = auth.getName();
//        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }


//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ApiException("User not found"));
//
//        String token = UUID.randomUUID().toString();
//        user.setResetToken(token);
//        user.setTokenExpiry(LocalDateTime.now().plusMinutes(30));
//        userRepository.save(user);
//        String resetLink = "http://localhost:5173/reset-password?token=" + token;
//        return ResponseEntity.ok(Map.of("resetLink", resetLink));
//    }
@Transactional
@PostMapping("/forgot-pssword")
public ResponseEntity<?> forgotPassword(@RequestBody String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ApiException("User not found"));

    String token = UUID.randomUUID().toString();

    user.setResetToken(token);
    user.setTokenExpiry(LocalDateTime.now().plusMinutes(30));

    System.out.println("TOKEN: " + token);

    userRepository.saveAndFlush(user);

    return ResponseEntity.ok(Map.of(
            "message", "Reset link generated",
            "token", token
    ));
}


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new ApiException("Invalid token"));


        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successful");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}


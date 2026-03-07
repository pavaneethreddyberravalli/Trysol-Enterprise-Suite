package org.trysol.Trysol.Auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.trysol.Trysol.Auth.Dto.AuthResponse;
import org.trysol.Trysol.Auth.Dto.LoginRequest;
import org.trysol.Trysol.Auth.Dto.UserRequest;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.entity.User;
import org.trysol.Trysol.Auth.service.UserService;
import org.trysol.Trysol.security.JwtUtil;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final  UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtil.generateToken(request.getUsernameOrEmail());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }

}

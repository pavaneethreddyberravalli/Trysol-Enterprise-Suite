package org.trysol.Trysol.Auth.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.entity.User;
import org.trysol.Trysol.Auth.exception.InvalidPasswordException;
import org.trysol.Trysol.Auth.exception.InvalidUsernameOrEmailException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginInput = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsernameOrEmail(loginInput, loginInput)
                .orElseThrow(() -> new InvalidUsernameOrEmailException("Invalid username or email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getName()))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}




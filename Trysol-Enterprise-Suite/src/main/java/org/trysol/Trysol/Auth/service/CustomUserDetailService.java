package org.trysol.Trysol.Auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.entity.User;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService  implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginInput) {

        User user = userRepository
                .findByUsernameOrEmail(loginInput, loginInput)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()))
        );
    }
}


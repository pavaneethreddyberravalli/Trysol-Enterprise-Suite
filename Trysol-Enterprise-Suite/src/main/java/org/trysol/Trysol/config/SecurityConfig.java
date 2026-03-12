package org.trysol.Trysol.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.trysol.Trysol.Auth.service.CustomUserDetailService;
import org.trysol.Trysol.security.JwtFilter;

    @Configuration
    public class SecurityConfig {

        @Autowired
        private final CustomUserDetailService customUserDetailService;

        @Autowired
        private JwtFilter jwtFilter;



        public SecurityConfig(CustomUserDetailService customUserDetailService) {
            this.customUserDetailService = customUserDetailService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .cors(Customizer.withDefaults())
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers( "/api/auth/**").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/hr/**").hasRole("HR")
                            .requestMatchers("/api/finance/**").hasRole("FINANCE")
                            .requestMatchers("/api/sales/**").hasRole("SALES")
                            .requestMatchers("/api/trainer/**").hasRole("TRAINER")
                            .requestMatchers("/api/assets/**").hasRole("ASSET_ADMIN")
                            .requestMatchers("/api/manager/**").hasRole("MANAGER")
                            .anyRequest().authenticated()
                    )
                    .userDetailsService(customUserDetailService);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();



        }
    }
/////////"/api/users/signup**"


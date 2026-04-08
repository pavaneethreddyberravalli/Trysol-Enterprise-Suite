package org.trysol.Trysol.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.trysol.Trysol.Auth.Repository.UserRepository;
import org.trysol.Trysol.Auth.service.CustomAuthenticationProvider;
import org.trysol.Trysol.Auth.service.CustomUserDetailService;
import org.trysol.Trysol.security.JwtFilter;

    @Configuration
    @EnableMethodSecurity(prePostEnabled = true)

    public class SecurityConfig {

        private final  CustomUserDetailService customUserDetailService;
        private final JwtFilter jwtFilter;

        public SecurityConfig(CustomUserDetailService customUserDetailService, JwtFilter jwtFilter) {
            this.customUserDetailService = customUserDetailService;
            this.jwtFilter=jwtFilter;
}

        @Bean
        public CustomAuthenticationProvider customAuthenticationProvider(
                UserRepository userRepository,
                PasswordEncoder passwordEncoder) {

            return new CustomAuthenticationProvider(userRepository, passwordEncoder);
        }




        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,CustomAuthenticationProvider customAuthenticationProvider) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .cors(Customizer.withDefaults())
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers( "/api/auth/**").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll()
                            //.requestMatchers("/api/invoice/download").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/finance/download").hasRole("ADMIN")
                            .requestMatchers("/api/finance/create").hasAnyRole("ADMIN","FINANCE")
                            .requestMatchers("/api/hr/**").hasRole("HR")
                            .requestMatchers("/api/finance/**").hasAnyRole("ADMIN","FINANCE")
                            .requestMatchers("/api/sales/**").hasRole("SALES")
                            .requestMatchers("/api/trainer/**").hasRole("TRAINER")
                            .requestMatchers("/api/assets/**").hasRole("ASSET_ADMIN")
                            .requestMatchers("/api/manager/**").hasRole("MANAGER")
                            .anyRequest().authenticated()
                    )
                    //.userDetailsService(customUserDetailService);
                        .authenticationProvider(customAuthenticationProvider);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();



        }
    }
/////////"/api/users/signup**"


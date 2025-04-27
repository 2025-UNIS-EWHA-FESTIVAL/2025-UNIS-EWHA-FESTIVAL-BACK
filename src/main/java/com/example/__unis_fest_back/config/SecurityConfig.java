package com.example.__unis_fest_back.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPasswordBase64;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Starting security filter chain setup...");

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    log.info("Session creation policy set to ALWAYS.");
                })
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/draw/**").permitAll()
                            .requestMatchers("/health").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/test").permitAll()
                            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().permitAll();
                    log.info("Authorization rules applied.");
                })
                .requiresChannel(channel -> {
                    channel
                            .requestMatchers("/health").requiresInsecure()
                            .anyRequest().requiresSecure();
                    log.info("Channel security settings applied.");
                })
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 URL
                        .loginProcessingUrl("/login")  // 로그인 인증 POST 요청 처리 URL
                        .defaultSuccessUrl("/admin/", true)  // 로그인 성공 후 리다이렉트 URL
                        .permitAll()
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout");  // 로그아웃 URL 설정
                    log.info("Logout URL set to /logout.");
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Initializing password encoder.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        log.info("Configuring user details service for admin.");
        log.info("✅ Registered ADMIN_USERNAME = {}", adminUsername);
        log.info("✅ Registered ADMIN_PASSWORD (already bcrypt) = {}", adminPasswordBase64);

        return new InMemoryUserDetailsManager(
                User.builder()
                        .username(adminUsername)
                        .password(adminPasswordBase64)  // Base64로 제공된 패스워드 직접 사용
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        log.info("Authentication Manager initialized.");
        return authenticationManager;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        log.info("Configuring authentication success handler.");
        return (request, response, authentication) -> {
            log.info("Login successful for user: {}", authentication.getName());
            response.sendRedirect("/admin/");
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        log.error("Login failed. Incorrect credentials or other issue.");
        return (request, response, exception) -> {
            log.error("Login attempt failed. Reason: {}", exception.getMessage());
            response.sendRedirect("/login?error");
        };
    }
}

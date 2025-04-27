package com.example.__unis_fest_back.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
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
        config.setAllowedOriginPatterns(List.of("*"));  // **모든 오리진 허용 temporarily**
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/draw/**").permitAll()
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/admin/**").authenticated()
                        .requestMatchers("/login").permitAll() // 로그인 페이지 요청 허용
                        .requestMatchers("/test").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().permitAll()
                )
                .requiresChannel(channel -> channel
                        .requestMatchers("/health").requiresInsecure()
                        .anyRequest().requiresSecure()
                )
                .formLogin(form -> form
                        .loginPage("/login") // GET 요청만 담당 (로그인 폼 보여줄 때)
                        .loginProcessingUrl("/login") // POST 요청으로 로그인 인증할 때
                        .defaultSuccessUrl("/admin/", true) // 로그인 성공하면 여기로 리다이렉트
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
                .build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        String decodedPassword = new String(Base64.getDecoder().decode(adminPasswordBase64)).trim();
        log.info("✅ 등록된 ADMIN_USERNAME = {}", adminUsername);
        log.info("✅ 등록된 ADMIN_PASSWORD (decoded bcrypt) = {}", decodedPassword);

        return new InMemoryUserDetailsManager(
                User.builder()
                        .username(adminUsername)
                        .password(decodedPassword) // 디코딩된 bcrypt를 그대로 사용
                        .roles("ADMIN")
                        .build()
        );
    }
}


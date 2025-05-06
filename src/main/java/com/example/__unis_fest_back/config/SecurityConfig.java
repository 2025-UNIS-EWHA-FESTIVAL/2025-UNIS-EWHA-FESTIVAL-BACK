package com.example.__unis_fest_back.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
    private String adminPassword;  // 이미 BCrypt로 저장된 값

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://2025-unis-festival.vercel.app",
                "https://2025-unis-fest-back.site"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 이게 true면 allowedOrigins에 * 못 씀
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new ForwardedHeaderFilter(), ChannelProcessingFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                // 세션은 기본 IF_REQUIRED
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout", "/test", "/health").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ OPTIONS 열기
                        .requestMatchers("/api/**").permitAll() // ✅ 너가 carousel에서 쓰는 API
                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**") // API 요청 → 401
                        )
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new AntPathRequestMatcher("/admin/**") // 관리자 페이지 요청 → /login 리디렉션
                        )
                )
                // HTTPS 강제화
                .requiresChannel(ch -> ch
                        // health는 여전히 HTTP만 허용
                        .requestMatchers("/health").requiresInsecure()
                        // 로그인폼, 로그인 처리, 관리자 페이지만 HTTPS로 강제
                        .requestMatchers("/login", "/login?error", "/admin**").requiresSecure()
                        // 나머지는 아무 제한 없이
                        .anyRequest().requiresSecure()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin", true)
                        .failureHandler((req, res, ex) -> {
                            // 실패 이유를 로그로 찍고
                            log.error("로그인 실패: {}", ex.getMessage());
                            // 원래 하던 대로 리다이렉트
                            res.sendRedirect("/login?error");
                        })
                        .permitAll()
                )
                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder pwEnc) {
        // adminPassword에는 이미 BCrypt 해시가 담겨 있다고 가정
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password(adminPassword)
                        .roles("ADMIN")
                        .build()
        );
    }
}

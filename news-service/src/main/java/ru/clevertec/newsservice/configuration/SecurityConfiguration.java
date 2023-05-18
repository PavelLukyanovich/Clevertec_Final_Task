package ru.clevertec.newsservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.clevertec.newsservice.service.security.jwt.JwtTokenFilter;
import ru.clevertec.newsservice.service.security.jwt.JwtTokenProvider;
import ru.clevertec.newsservice.util.Role;
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web
                .ignoring().requestMatchers("/api/v1/authentication/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("In filterChain-------");
        httpSecurity
                .csrf().disable()
                .cors()
                .and()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.info("Request|| = {}", request.getHeader("Authorization"));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Unauthorized!");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Forbidden!");
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authentication/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/news").hasAnyAuthority(Role.ADMIN.name(), Role.JOURNALIST.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/comments").hasAnyAuthority(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/news").hasAnyAuthority(Role.ADMIN.name(), Role.JOURNALIST.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/comments").hasAnyAuthority(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/news").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/comments").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/news").hasAnyAuthority(Role.ADMIN.name(), Role.JOURNALIST.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/comments").hasAnyAuthority(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/comments/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.JOURNALIST.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/news").anonymous()
                .requestMatchers(HttpMethod.GET, "/api/v1/comments").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

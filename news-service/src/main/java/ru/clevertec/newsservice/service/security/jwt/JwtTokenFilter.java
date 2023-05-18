package ru.clevertec.newsservice.service.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.clevertec.newsservice.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("----In doFilter----");
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        log.info("Servlet request = {}", ((HttpServletRequest) servletRequest).getHeader("Authorization"));
        log.info("Bearer token = {}", bearerToken);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            log.info("Bearer token without BEARER________ = {}", bearerToken);
        }
        if (Objects.nonNull(bearerToken) && jwtTokenProvider.validateToken(bearerToken)) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                log.info("Authentication = {}", authentication);
                if (Objects.nonNull(authentication)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("Security context = {}", SecurityContextHolder.getContext().getAuthentication());
                }
            } catch (ResourceNotFoundException ignored) {
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

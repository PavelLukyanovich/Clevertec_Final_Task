package ru.clevertec.newsservice.service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.clevertec.newsservice.model.dto.UserDetailsDto;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;

    public String createAccessToken(Authentication authentication) {
        log.info("!!! In createAccessToken incoming authentication = {}", authentication);
        UserDetailsDto principal = (UserDetailsDto) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(principal.getLogin());
        log.info("!!!Claims = {}", claims);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return generateToken(claims, now, validity);
    }

    private String generateToken(Claims claims, Date now, Date validity) {
        log.info("!!!In generateToken()/// Claims = {}", claims);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    public boolean validateToken(String token) {
        log.info("!!!In validateToken()/// Token = {}", token);
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret()))
                .parseClaimsJws(token);
        log.info("!!!Fetched token = {}", token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getName(String token) {
        log.info("!!!In getName()--- token = {}", token);
        return Jwts
                .parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public Authentication getAuthentication(String token) {
        log.info("!!!In getAuthentication---");
        String login = getName(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

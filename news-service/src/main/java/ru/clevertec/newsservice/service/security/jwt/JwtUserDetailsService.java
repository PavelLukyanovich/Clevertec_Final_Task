package ru.clevertec.newsservice.service.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.clevertec.newsservice.model.entity.User;
import ru.clevertec.newsservice.model.feign.UserResponseFeignClientApi;
import ru.clevertec.newsservice.service.user.UserFeignService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return Optional.ofNullable(userFeignService.getUserByName(username))
                .map(UserResponseFeignClientApi::getData)
                .map(JwtEntityFactory::create)
                .orElseThrow(() -> new BadCredentialsException(username));
    }

}

package ru.clevertec.newsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsservice.model.entity.User;
import ru.clevertec.newsservice.model.feign.UserResponseFeignClientApi;
import ru.clevertec.newsservice.model.request.authentification.JwtRequest;
import ru.clevertec.newsservice.model.request.authentification.RegisterUserRequest;
import ru.clevertec.newsservice.model.response.JwtResponse;
import ru.clevertec.newsservice.model.response.ResponseApi;
import ru.clevertec.newsservice.service.security.authentication.AuthenticationService;
import ru.clevertec.newsservice.service.user.UserFeignService;
import ru.clevertec.newsservice.util.mapper.user.UserMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@Validated
//@Profile("dev")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserFeignService userFeignService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        log.info("JwtResponse = {}", authenticationService.login(loginRequest));
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseApi<User> register(@Validated @RequestBody RegisterUserRequest request) {
        log.info("Request = {}", request);
        User user = userMapper.userDtoToUser(request);
        log.info("User is {}", user);
        UserResponseFeignClientApi createdUser = userFeignService.createUser(request);
        return new ResponseApi<>(createdUser.getData());
    }

}

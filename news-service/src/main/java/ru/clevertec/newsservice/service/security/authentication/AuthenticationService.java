package ru.clevertec.newsservice.service.security.authentication;

import ru.clevertec.newsservice.model.request.authentification.JwtRequest;
import ru.clevertec.newsservice.model.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse login(JwtRequest loginRequest);


}

package ru.clevertec.newsservice.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {

    private String login;

    private String accessToken;

}

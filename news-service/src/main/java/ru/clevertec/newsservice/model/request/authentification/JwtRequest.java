package ru.clevertec.newsservice.model.request.authentification;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
    @NotNull(message = "login must be not null")
    private String login;
    @NotNull(message = "password must be not null")
    private String password;
}

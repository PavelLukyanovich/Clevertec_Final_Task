package ru.clevertec.newsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ru.clevertec.newsservice.util.Role;

import java.util.UUID;

@Data
public class User {

    private UUID id;

    private String name;

    private String login;

    private String password;
    
    private Role role;
}

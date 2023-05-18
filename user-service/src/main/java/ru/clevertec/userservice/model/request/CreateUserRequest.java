package ru.clevertec.userservice.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.clevertec.userservice.util.Role;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateUserRequest {

    private String name;
    private String login;
    private String password;
    private Role role;
}

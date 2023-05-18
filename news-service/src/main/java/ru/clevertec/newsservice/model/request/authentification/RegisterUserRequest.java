package ru.clevertec.newsservice.model.request.authentification;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.clevertec.newsservice.util.Role;

@Data
public class RegisterUserRequest {

    private String name;

    private String login;
    @Base64Decoder
    private String password;

    private Role role;


    public void setPassword(String password) {

        this.password = new BCryptPasswordEncoder().encode(password);

    } // TODO: 12.05.2023 moov to @ 

}

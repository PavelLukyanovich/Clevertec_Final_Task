package ru.clevertec.newsservice.service.security.jwt;

import ru.clevertec.newsservice.model.dto.UserDetailsDto;
import ru.clevertec.newsservice.model.entity.User;
import ru.clevertec.newsservice.util.Role;

public class JwtEntityFactory {

    public static UserDetailsDto create(User user) {
        return new UserDetailsDto(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole())
        );
    }

    private static Role mapToGrantedAuthorities(Role role) {
        return role;
    }


}

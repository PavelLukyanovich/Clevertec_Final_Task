package ru.clevertec.userservice.model.dto;

import ru.clevertec.userservice.util.Role;

import java.util.UUID;

public record UserDto(UUID id, String name, String login, String password, Role role) {
}

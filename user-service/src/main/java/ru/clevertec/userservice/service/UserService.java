package ru.clevertec.userservice.service;

import ru.clevertec.userservice.model.dto.UserDto;
import ru.clevertec.userservice.model.request.CreateUserRequest;
import ru.clevertec.userservice.model.request.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getUserById(UUID id);

    List<UserDto> getAllUsers();

    UserDto saveUser(CreateUserRequest request);

    UserDto deleteUser(UUID id);

    UserDto updateUser(UpdateUserRequest request);

    UserDto getUserByName(String username);
}


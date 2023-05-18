package ru.clevertec.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.userservice.exception.UserExistsException;
import ru.clevertec.userservice.exception.UserNotFoundException;
import ru.clevertec.userservice.model.dto.UserDto;
import ru.clevertec.userservice.model.entity.User;
import ru.clevertec.userservice.model.request.CreateUserRequest;
import ru.clevertec.userservice.model.request.UpdateUserRequest;
import ru.clevertec.userservice.repository.UserRepository;
import ru.clevertec.userservice.util.mapper.UserMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDto getUserById(UUID id) {

        log.info("--getUserById fetched id = {}", id);
        UserDto userDto = UserMapper.INSTANCE.userToUserDto(userRepository.getReferenceById(id));
        log.info("--got user = {} ", userDto);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> userDtos = userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .toList();
        log.info("--got users list = {} ", userDtos);
        return userDtos;
    }

    @Override
    public UserDto saveUser(CreateUserRequest request) {

        User user = UserMapper.INSTANCE.requestToUser(request);

        User userByLogin = userRepository.findUserByLogin(request.getLogin());
        if (Objects.nonNull(userByLogin)) {
            log.info("UserExist exception user = {}", user);
            throw new UserExistsException("User already exist");

        }
        User savedUser = userRepository.save(user);
        log.info("SavedUser = {}", savedUser);

        return UserMapper.INSTANCE.userToUserDto(savedUser);

    }

    @Override
    public UserDto deleteUser(UUID id) {

        log.info("--deleteUser fetched id = {}", id);
        User byId = getUserIfExist(id);
        log.info("--userById = {}", byId);
        userRepository.deleteById(id);

        return UserMapper.INSTANCE.userToUserDto(byId);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest request) {

        User byId = getUserIfExist(request.getId());
        log.info("--fetched user = {}", byId);
        byId.setLogin(request.getLogin());
        byId.setName(request.getName());
        byId.setPassword(request.getPassword());
        byId.setRole(request.getUserRole());
        log.info("--user after update = {}", byId);

        return UserMapper.INSTANCE.userToUserDto(userRepository.save(byId));
    }

    @Override
    public UserDto getUserByName(String userName) {

        log.info("--user name = {}", userName);
        User userByName = userRepository.findUserByLogin(userName);
        log.info("--user = {}", userByName);
        return UserMapper.INSTANCE.userToUserDto(userByName);
    }

    private User getUserIfExist(UUID id) {

        log.info("--user id = {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}

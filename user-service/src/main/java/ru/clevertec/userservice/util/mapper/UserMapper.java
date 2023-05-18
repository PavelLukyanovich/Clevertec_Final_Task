package ru.clevertec.userservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.clevertec.userservice.model.dto.UserDto;
import ru.clevertec.userservice.model.entity.User;
import ru.clevertec.userservice.model.request.CreateUserRequest;
import ru.clevertec.userservice.model.request.UserRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User requestToUser(CreateUserRequest userRequest);
}

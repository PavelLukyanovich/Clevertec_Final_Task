package ru.clevertec.newsservice.util.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsservice.model.dto.UserDetailsDto;
import ru.clevertec.newsservice.model.entity.User;
import ru.clevertec.newsservice.model.request.authentification.RegisterUserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //  @Mapping(target = "authorities", ignore = true)
    User requestToUser(RegisterUserRequest request);

    User userDtoToUser(RegisterUserRequest request);

}

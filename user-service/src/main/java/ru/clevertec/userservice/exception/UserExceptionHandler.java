package ru.clevertec.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.userservice.model.response.ErrorResponse;

import javax.management.BadAttributeValueExpException;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse<UUID> handleUserNotFoundException(UserNotFoundException e) {

        log.warn("Fetched handleUserNotFoundException {}", e.getMessage());
        return new ErrorResponse<>(e.getUserId(), "user not found");
    }

    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(UserExistsException.class)
    public ErrorResponse<BadAttributeValueExpException> handleUserExistException(UserExistsException e) {

        log.warn(String.valueOf(e.getCause()));
        return new ErrorResponse<>(new BadAttributeValueExpException(e),"user with current id already exists");
        // TODO: 12.05.2023  
    }
}

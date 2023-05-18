package ru.clevertec.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserExistsException extends RuntimeException {

    private String message;
}

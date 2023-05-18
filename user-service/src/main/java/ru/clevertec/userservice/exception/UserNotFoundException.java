package ru.clevertec.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserNotFoundException extends RuntimeException {

    private UUID userId;
}

package ru.clevertec.userservice.model.response;

import java.util.UUID;

public record ErrorResponse<T>(T data, String message) {

    private static UUID userId;
}

package ru.clevertec.newsservice.model.response;

public record ErrorResponse<T>(T data, String message) {
}

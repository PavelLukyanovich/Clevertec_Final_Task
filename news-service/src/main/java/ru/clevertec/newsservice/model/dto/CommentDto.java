package ru.clevertec.newsservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(UUID id,

                         @JsonSerialize(using = LocalDateTimeSerializer.class)
                         @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime time,
                         String text,
                         UUID userId,
                         String userName,
                         UUID newsId) {
}

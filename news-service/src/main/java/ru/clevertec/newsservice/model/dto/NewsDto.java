package ru.clevertec.newsservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import ru.clevertec.newsservice.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record NewsDto(UUID id,
                      @JsonSerialize(using = LocalDateTimeSerializer.class)
                      @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime time,
                      String title,
                      String text,
                      UUID userId,
                      List<Comment> commentList) {
}

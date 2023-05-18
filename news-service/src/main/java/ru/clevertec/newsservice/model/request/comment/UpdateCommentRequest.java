package ru.clevertec.newsservice.model.request.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UpdateCommentRequest extends CommentRequest {

    private UUID id;
    private String text;
    private UUID userId;
    private String username;
}

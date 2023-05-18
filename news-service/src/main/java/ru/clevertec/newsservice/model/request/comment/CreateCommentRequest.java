package ru.clevertec.newsservice.model.request.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateCommentRequest extends CommentRequest {

    private String text;
    private UUID userId;
    private String userName;
    private UUID newsId;
}

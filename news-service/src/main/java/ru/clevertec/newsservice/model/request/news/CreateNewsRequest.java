package ru.clevertec.newsservice.model.request.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.clevertec.newsservice.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@RequiredArgsConstructor
public class CreateNewsRequest extends NewsRequest {

    private String title;
    private String text;
    private UUID userId;
    public List<Comment> commentList;
}

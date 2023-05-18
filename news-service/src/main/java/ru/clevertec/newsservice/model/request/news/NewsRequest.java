package ru.clevertec.newsservice.model.request.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.clevertec.newsservice.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class NewsRequest {

}

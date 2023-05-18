package ru.clevertec.newsservice.service.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.request.comment.CreateCommentRequest;
import ru.clevertec.newsservice.model.request.comment.UpdateCommentRequest;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    CommentDto saveComment(CreateCommentRequest request);

    List<CommentDto> getAllComments();
    List<Comment> searchComment(String text, List<String> fields, int limit);

    CommentDto getCommentById(UUID uuid);

    Page<CommentDto> getCommentsByNewsId(UUID newsId, Pageable pageable);

    CommentDto deleteComment(UUID id);

    CommentDto updateComment(UpdateCommentRequest request);

}

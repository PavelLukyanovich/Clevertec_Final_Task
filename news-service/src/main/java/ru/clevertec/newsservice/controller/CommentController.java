package ru.clevertec.newsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.request.comment.CreateCommentRequest;
import ru.clevertec.newsservice.model.request.SearchRequest;
import ru.clevertec.newsservice.model.request.comment.UpdateCommentRequest;
import ru.clevertec.newsservice.model.response.ResponseApi;
import ru.clevertec.newsservice.service.comment.CommentService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("#request.userName == authentication.name")
    public ResponseApi<CommentDto> createComment(@Validated @RequestBody CreateCommentRequest request) {

        log.info("###POST CreateCommentRequest = {}", request);
        CommentDto commentDto = commentService.saveComment(request);
        return new ResponseApi<>(commentDto);
    }

    @GetMapping
    public ResponseApi<List<CommentDto>> getAllComments() {

        log.info("###GET getAllComments");
        return new ResponseApi<>(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseApi<CommentDto> getCommentById(@PathVariable UUID id) {

        log.info("###GET fetched id = {}", id);
        return new ResponseApi<>(commentService.getCommentById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@commentServiceImpl.getCommentById(id).userName == authentication.name")
    public ResponseApi<CommentDto> deleteComment(@PathVariable UUID id) {

        log.info("###DELETE fetched id = {}", id);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("User name = {}", name);
        return new ResponseApi<>(commentService.deleteComment(id));
    }

    @PutMapping
    @PreAuthorize("@request.username == authentication.name")
    public ResponseApi<CommentDto> updateComment(@Validated @RequestBody UpdateCommentRequest request) {

        log.info("###PUT updateCommentRequest = {}", request);
        return new ResponseApi<>(commentService.updateComment(request));

    }
    @GetMapping("/search")
    public List<Comment> searchPlants(SearchRequest searchRequest) {

        log.info("Request for plant search received with data : " + searchRequest);

        return commentService.searchComment(searchRequest.getText(), List.of(searchRequest.getFields()), searchRequest.getLimit());
    }

}

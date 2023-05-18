package ru.clevertec.newsservice.service.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.request.comment.CreateCommentRequest;
import ru.clevertec.newsservice.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void whenSearchTest_shouldReturnValidAnswer() {
        String text = "test";
        List<String> fields = new ArrayList<>();
        int limit = 10;

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUserName("user");

        List<Comment> comments = List.of(comment);

        Mockito.when(commentRepository.searchBy(text, limit, fields.toArray(new String[0]))).thenReturn(comments);

        List<Comment> result = commentService.searchComment(text, fields, limit);

        Assertions.assertEquals(comments.size(), result.size());
        Assertions.assertEquals(comments.get(0).getText(), result.get(0).getText());
    }

    @Test
    public void whenCommentCreated_shouldReturnValidTextFromCreatedComment() {
        CreateCommentRequest request = new CreateCommentRequest();
        request.setText("test");
        request.setUserName("user");

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setUserName(request.getUserName());
        comment.setTime(LocalDateTime.now());

        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.saveComment(request);

        Assertions.assertEquals(comment.getText(), result.text());
    }

    @Test
    public void whenGetAllComments_shouldReturnValidCommentList() {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        Mockito.when(commentRepository.findAll()).thenReturn(comments);

        List<CommentDto> result = commentService.getAllComments();

        Assertions.assertEquals(comments.size(), result.size());
    }

    @Test
    public void whenGetCommentById_shouldReturnValidComment() {
        UUID id = UUID.randomUUID();
        Comment comment = new Comment();
        Mockito.when(commentRepository.getReferenceById(id)).thenReturn(comment);

        CommentDto result = commentService.getCommentById(id);

        Assertions.assertNotNull(result);
    }

    @Test
    public void whenGetCommentByNewsId_shouldReturnAllCommentWithValidId() {
        UUID newsId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comment> commentList = new PageImpl<>(new ArrayList<>());
        Mockito.when(commentRepository.findAllByNewsId(newsId, pageable)).thenReturn(commentList);

        Page<CommentDto> result = commentService.getCommentsByNewsId(newsId, pageable);
        List<CommentDto> list = result.getContent();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(newsId, list.get(0).id());
    }

}
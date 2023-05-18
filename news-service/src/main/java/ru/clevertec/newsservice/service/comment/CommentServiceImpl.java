package ru.clevertec.newsservice.service.comment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsservice.exception.ResourceNotFoundException;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.request.comment.CreateCommentRequest;
import ru.clevertec.newsservice.model.request.comment.UpdateCommentRequest;
import ru.clevertec.newsservice.repository.CommentRepository;
import ru.clevertec.newsservice.repository.SearchRepository;
import ru.clevertec.newsservice.util.mapper.comment.CommentMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@Cacheable(cacheNames = "commentCache")
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("text", "userName");

    public List<Comment> searchComment(String text, List<String> fields, int limit) {

        List<String> fieldsToSearchBy = fields.isEmpty() ? SEARCHABLE_FIELDS : fields;

        boolean containsInvalidField = fieldsToSearchBy.stream().anyMatch(f -> !SEARCHABLE_FIELDS.contains(f));

        if (containsInvalidField) {
            throw new IllegalArgumentException();
        }

        return commentRepository.searchBy(
                text, limit, fieldsToSearchBy.toArray(new String[0]));
}

    @Override
    @Transactional
    @CacheEvict(cacheNames = "comments", allEntries = true)
    public CommentDto saveComment(CreateCommentRequest request) {
        log.info("&&&&Save comment --------");
        Comment comment = CommentMapper.INSTANCE.commentRequestToComment(request);
        log.info("&&&&Comment = {}", comment);

        Comment savedComment = commentRepository.save(comment);

        savedComment.setTime(LocalDateTime.now());
        savedComment.setText(request.getText());
        savedComment.setUserId(request.getUserId());
        savedComment.setUserName(request.getUserName());
        savedComment.setNewsId(request.getNewsId());
        log.info("&&&&Save comment = {}", savedComment);
        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(savedComment);
        log.info("&&&&CommentDto = {}", commentDto);

        return commentDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "comments")
    public List<CommentDto> getAllComments() {

        List<Comment> commentList = commentRepository.findAll();
        List<CommentDto> commentDtos = commentList.stream()
                .map(CommentMapper.INSTANCE::commentToCommentDto)
                .toList();

        return commentDtos;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "comments", key = "#id", unless = "#result == null")
    public CommentDto getCommentById(UUID id) {

        Comment comment = commentRepository.getReferenceById(id);

        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(comment);

        return commentDto;
    }

    @Override
    @Transactional
    public Page<CommentDto> getCommentsByNewsId(UUID newsId, Pageable pageable) {

        Page<Comment> commentList = commentRepository.findAllByNewsId(newsId, pageable);
        List<CommentDto> commentDtos = commentList.stream()
                .map(CommentMapper.INSTANCE::commentToCommentDto)
                .toList();
        return new PageImpl<>(commentDtos);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "comments", key = "#id"),
            @CacheEvict(cacheNames = "comments", allEntries = true)})
    public CommentDto deleteComment(UUID id) {

        Comment byId = getCommentIfExist(id);

        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(byId);
        commentRepository.deleteById(id);

        return commentDto;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "comments", allEntries = true)
    public CommentDto updateComment(UpdateCommentRequest request) {

        Comment byId = getCommentIfExist(request.getId());

        byId.setText(request.getText());
        byId.setUserName(request.getUsername());


        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(byId);

        return commentDto;
    }

    private Comment getCommentIfExist(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}

package ru.clevertec.newsservice.util.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.request.comment.CommentRequest;
import ru.clevertec.newsservice.model.request.comment.CreateCommentRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto commentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    Comment commentRequestToComment(CreateCommentRequest request);

}

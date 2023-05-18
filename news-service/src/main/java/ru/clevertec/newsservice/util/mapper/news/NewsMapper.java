package ru.clevertec.newsservice.util.mapper.news;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsservice.model.dto.NewsDto;
import ru.clevertec.newsservice.model.entity.News;
import ru.clevertec.newsservice.model.request.news.CreateNewsRequest;
import ru.clevertec.newsservice.model.request.news.NewsRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDto newsToNewsDto(News news);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    News newsRequestToNews(CreateNewsRequest request);


}

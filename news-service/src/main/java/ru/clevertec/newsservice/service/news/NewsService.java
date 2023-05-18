package ru.clevertec.newsservice.service.news;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsservice.model.dto.NewsDto;
import ru.clevertec.newsservice.model.entity.News;
import ru.clevertec.newsservice.model.request.news.CreateNewsRequest;
import ru.clevertec.newsservice.model.request.news.UpdateNewsRequest;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsDto saveNews(CreateNewsRequest request);

    Page<NewsDto> getAllNews(Pageable pageable);

    List<News> searchNews(String text, List<String> fields, int limit);

    NewsDto getNewsById(UUID newsId, Integer offset, Integer limit);

    NewsDto updateNews(UpdateNewsRequest request);

    NewsDto deleteNews(UUID id);
}

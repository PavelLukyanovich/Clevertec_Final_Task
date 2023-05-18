package ru.clevertec.newsservice.service.news;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.newsservice.model.dto.NewsDto;
import ru.clevertec.newsservice.model.entity.News;
import ru.clevertec.newsservice.model.request.news.CreateNewsRequest;
import ru.clevertec.newsservice.model.request.news.UpdateNewsRequest;
import ru.clevertec.newsservice.repository.NewsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NewsService newsService;

    @Test
    public void whenNewsCreated_shouldReturnValidTextFromCreatedNews() {
        CreateNewsRequest request = new CreateNewsRequest();
        request.setText("test");

        News news = new News();
        news.setText(request.getText());

        when(newsRepository.save(Mockito.any(News.class))).thenReturn(news);

        NewsDto result = newsService.saveNews(request);

        assertEquals(news.getText(), result.text());
    }

    @Test
    public void testUpdateNews() {
        UpdateNewsRequest request = new UpdateNewsRequest();
        UUID id = UUID.fromString("1");
        request.setNewsId(id);
        request.setTitle("Test title");
        request.setText("Test text");

        News news = new News();
        news.setId(id);
        news.setTitle("Old title");
        news.setText("Old text");

        when(newsRepository.findById(request.getNewsId())).thenReturn(Optional.of(news));

        NewsDto newsDto = newsService.updateNews(request);

        assertEquals(request.getTitle(), news.getTitle());
        assertEquals(request.getText(), news.getText());
    }
}
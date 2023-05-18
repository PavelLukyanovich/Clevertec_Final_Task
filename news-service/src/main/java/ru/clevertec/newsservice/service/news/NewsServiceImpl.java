package ru.clevertec.newsservice.service.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsservice.exception.ResourceNotFoundException;
import ru.clevertec.newsservice.model.dto.CommentDto;
import ru.clevertec.newsservice.model.dto.NewsDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.entity.News;
import ru.clevertec.newsservice.model.request.news.CreateNewsRequest;
import ru.clevertec.newsservice.model.request.news.UpdateNewsRequest;
import ru.clevertec.newsservice.repository.NewsRepository;
import ru.clevertec.newsservice.service.comment.CommentService;
import ru.clevertec.newsservice.util.mapper.comment.CommentMapper;
import ru.clevertec.newsservice.util.mapper.news.NewsMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "newsCache")
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final CommentService commentService;
    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("title", "text");

    public List<News> searchNews(String text, List<String> fields, int limit) {

        List<String> fieldsToSearchBy = fields.isEmpty() ? SEARCHABLE_FIELDS : fields;

        boolean containsInvalidField = fieldsToSearchBy.stream().anyMatch(f -> !SEARCHABLE_FIELDS.contains(f));

        if (containsInvalidField) {
            throw new IllegalArgumentException();
        }

        return newsRepository.searchBy(
                text, limit, fieldsToSearchBy.toArray(new String[0]));
    }

    @Override
    @CacheEvict(cacheNames = "news", allEntries = true)
    @Transactional
    public NewsDto saveNews(CreateNewsRequest request) {
        log.info("%%%Request = {}", request);
        News news = NewsMapper.INSTANCE.newsRequestToNews(request);
        log.info("%%%News = {}", news);
        News savedNews = newsRepository.save(news);
        log.info("%%%Saved news = {}", savedNews);
        NewsDto newsDto = NewsMapper.INSTANCE.newsToNewsDto(savedNews);
        log.info("%%%NewsDto = {}", savedNews);
        return newsDto;

    }

    @Override
    @Cacheable(cacheNames = "news")
    @Transactional(readOnly = true)
    public Page<NewsDto> getAllNews(Pageable pageable) {

        List<News> newsList = newsRepository.findAll();
        log.info("%%%GetAll - News list = {}", newsList);
        List<NewsDto> newsDtosList = newsList.stream()
                .map(NewsMapper.INSTANCE::newsToNewsDto)
                .toList();
        Page<NewsDto> newsDtos = new PageImpl<>(newsDtosList);
        log.info("%%%NewsDto list = {}", newsDtos);

        return newsDtos;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "news", key = "#id", unless = "#result == null")
    public NewsDto getNewsById(UUID newsId, Integer offset, Integer limit) {
        log.info("%%%News Id = {}", newsId);
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News not found"));
        log.info("%%%Fetched news = {}", news);
        Page<CommentDto> commentPageDtos = commentService.getCommentsByNewsId(newsId, PageRequest.of(offset, limit));
        List<CommentDto> commentListDtos = commentPageDtos.getContent();
        List<Comment> commentList = commentListDtos.stream()
                .map(CommentMapper.INSTANCE::commentDtoToComment)
                .toList();
        news.setCommentList(commentList);
        NewsDto newsDto = NewsMapper.INSTANCE.newsToNewsDto(news);
        log.info("%%%NewsDto = {}", newsDto);
        return newsDto;
    }

    @Override
    @CacheEvict(cacheNames = "news", allEntries = true)
    @Transactional
    public NewsDto updateNews(UpdateNewsRequest request) {
        log.info("%%%Update request = {}", request);
        News byId = getNewsIfExist(request.getNewsId());
        log.info("%%%News by id = {}", byId);
        byId.setTitle(request.getTitle());
        byId.setText(request.getText());
        log.info("%%%News after update = {}", byId);

        newsRepository.save(byId);

        NewsDto newsDto = NewsMapper.INSTANCE.newsToNewsDto(byId);
        log.info("%%%NewsDto = {}", newsDto);
        return newsDto;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "news", key = "#id"),
            @CacheEvict(cacheNames = "news", allEntries = true)})
    public NewsDto deleteNews(UUID id) {
        log.info("%%%Incoming id = {}", id);
        News byId = getNewsIfExist(id);
        log.info("%%%News by id = {}", byId);
        NewsDto newsDto = NewsMapper.INSTANCE.newsToNewsDto(byId);
        log.info("%%%NewsDto = {}", newsDto);
        newsRepository.deleteById(id);

        return newsDto;
    }


    private News getNewsIfExist(UUID id) {
        News byId = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return byId;
    }
}

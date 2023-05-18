package ru.clevertec.newsservice.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsservice.model.dto.NewsDto;
import ru.clevertec.newsservice.model.entity.Comment;
import ru.clevertec.newsservice.model.entity.News;
import ru.clevertec.newsservice.model.request.SearchRequest;
import ru.clevertec.newsservice.model.request.news.CreateNewsRequest;
import ru.clevertec.newsservice.model.request.news.UpdateNewsRequest;
import ru.clevertec.newsservice.model.response.ResponseApi;
import ru.clevertec.newsservice.service.news.NewsService;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Validated
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    @PreAuthorize("@jwtUserDetailsService.loadUserByUsername(userName) == authentication.name")
    public ResponseApi<NewsDto> createNews(@Validated @RequestBody CreateNewsRequest request, @RequestParam String userName) {

        log.info("###POST createNewsRequest = {}", request);
        return new ResponseApi<>(newsService.saveNews(request));
    }

    @GetMapping
    public ResponseApi<Page<NewsDto>> getAllNews(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                 @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(20) Integer limit) {
        log.info("###GET ALL NEWS ");
        return new ResponseApi<>(newsService.getAllNews(PageRequest.of(offset, limit)));
    }

    @GetMapping("/{id}")
    public ResponseApi<NewsDto> getNewsById(@PathVariable UUID id,
                                            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(20) Integer limit) {

        log.info("###GET fetched id = {}", id);
        return new ResponseApi<>(newsService.getNewsById(id, offset, limit));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@jwtUserDetailsService.loadUserByUsername(userName) == authentication.name")
    public ResponseApi<NewsDto> deleteNews(@PathVariable UUID id, @RequestParam String userName) {

        log.info("###DELETE fetched id = {}", id);
        return new ResponseApi<>(newsService.deleteNews(id));
    }

    @PutMapping
    @PreAuthorize("@jwtUserDetailsService.loadUserByUsername(userName) == authentication.name")
    public ResponseApi<NewsDto> updateNews(@Validated @RequestBody UpdateNewsRequest request, @RequestParam String userName) {

        log.info("###PUT updateNewsRequest = {}", request);
        return new ResponseApi<>(newsService.updateNews(request));
    }
    @GetMapping("/search")
    public List<News> searchNews(SearchRequest searchRequest) {

        log.info("Request for plant search received with data : " + searchRequest);

        return newsService.searchNews(searchRequest.getText(), List.of(searchRequest.getFields()), searchRequest.getLimit());
    }
}

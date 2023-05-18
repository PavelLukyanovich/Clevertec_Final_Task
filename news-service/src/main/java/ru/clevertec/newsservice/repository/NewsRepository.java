package ru.clevertec.newsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsservice.model.entity.News;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>, SearchRepository<News, UUID> {

}

package ru.clevertec.newsservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsservice.model.entity.Comment;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID>, SearchRepository<Comment, UUID> {

    @Query("SELECT c FROM Comment c WHERE c.newsId = :newsId")
    Page<Comment> findAllByNewsId(UUID newsId, Pageable pageable);
}


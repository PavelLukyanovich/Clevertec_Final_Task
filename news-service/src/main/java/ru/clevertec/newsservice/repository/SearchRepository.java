package ru.clevertec.newsservice.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
@NoRepositoryBean
public interface SearchRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> searchBy(String text, int limit, String... fields);
}

package ru.clevertec.newsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Indexed
@Table(name = "news")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;
    @GeneratedValue()
    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time;
    @FullTextField
    @Column(name = "title")
    private String title;
    @FullTextField
    @Column(name = "text")
    private String text;
    @Column(name = "user_id")
    private UUID userId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    public List<Comment> commentList;
}

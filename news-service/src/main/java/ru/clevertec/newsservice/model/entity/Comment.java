package ru.clevertec.newsservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;
@Indexed
@Table(name = "comment")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;
    @Column(name = "time")
    private LocalDateTime time;
    @FullTextField()
    @Column(name = "text")
    private String text;
    @Column(name = "user_id")
    private UUID userId;
    @FullTextField()
    @Column(name = "user_name")
    private String userName;
    @Column(name = "news_id")
    private UUID newsId;

}

package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import kz.comics.account.model.like.CommentLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id", referencedColumnName = "id")
    private ComicEntity comicEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private LocalDate createdDate;

    // @Enumerated - to tell Spring Boot that is enum
    // By default it is EnumType.ORDINAL: 0, 1, 2
    @Enumerated(EnumType.STRING)
    private CommentLikeType likeType;
}

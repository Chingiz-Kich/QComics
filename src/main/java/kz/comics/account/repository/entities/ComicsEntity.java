package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import kz.comics.account.model.comics.ComicsType;
import kz.comics.account.model.comics.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comics")
public class ComicsEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    // @Enumerated - to tell Spring Boot that is enum
    // @CollectionTable annotation to specify the name of the table that will store the collection.
    // By default, it is EnumType.ORDINAL: 0, 1, 2
    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "book_genres")
    private Set<Genre> genres;

    @Column(name = "author")
    private String author;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

/*    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapters_id", referencedColumnName = "id")
    private List<ChapterEntity> chapters;*/

    @Column(name = "rating")
    private Float rating;

    @Lob
    @Column(name = "description")
    private String description;

    // @Enumerated - to tell Spring Boot that is enum
    // By default it is EnumType.ORDINAL: 0, 1, 2
    @Enumerated(EnumType.STRING)
    private ComicsType type;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "is_updated")
    private Boolean isUpdated;

}

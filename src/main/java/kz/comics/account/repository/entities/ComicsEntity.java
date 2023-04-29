package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import kz.comics.account.model.comics.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @Column(name = "name", unique = true)
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

    @OneToOne(cascade = CascadeType.ALL)
    private ImageCoverEntity cover;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapters_id", referencedColumnName = "id")
    private List<ChapterEntity> chapters;

    @Column(name = "likes")
    private Float rating;

    @Column(name = "rates")
    private Integer rates;
}

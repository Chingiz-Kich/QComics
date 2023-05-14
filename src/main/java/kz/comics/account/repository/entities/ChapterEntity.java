package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chapters")
public class ChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapter_sequence")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id", referencedColumnName = "id")
    private ComicsEntity comicsEntity;
}

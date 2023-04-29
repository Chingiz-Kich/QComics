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
@Table(name = "image_covers")
public class ImageCoverEntity {

    @Id
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Lob
    @Column(name = "data")
    private String data;
}

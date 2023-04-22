package kz.comics.account.model.comics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicsDto {
    private String name;
    private List<Genre> genres;
    private String author;
    private ImageEntity cover;
    private List<ImageEntity> chapters;
    private Integer likes;
}

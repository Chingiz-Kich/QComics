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
public class ComicSaveDto {
    private String name;
    private String author;
    private String imageCoverBase64;
    private String description;
    private List<Genre> genres;
}

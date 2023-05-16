package kz.comics.account.model.comics;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDto {
    private String name;
    private List<Genre> genres;
    private String author;
    private String imageCoverBase64;
    // private List<Integer> chapterIds;
    private Double rating;
    private Double votes;
    private String description;
    private ComicsType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate publishedDate;

    private Boolean isUpdated;
}

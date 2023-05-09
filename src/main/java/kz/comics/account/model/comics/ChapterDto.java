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
public class ChapterDto {

    private Integer id;
    private String name;
    private String comicName;

    // FIXME: для чего я забыл
    //private String comicsName;

    private List<Integer> imageIds;
}

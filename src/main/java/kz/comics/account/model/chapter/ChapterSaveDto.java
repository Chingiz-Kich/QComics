package kz.comics.account.model.chapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSaveDto {
    private String name;
    private String comicName;
    private Integer comicId;
}

package kz.comics.account.model.chapter;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSaveByCId extends ChapterSaveDto {
    private Integer comicId;
}

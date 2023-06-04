package kz.comics.account.model.chapter;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSaveByCName extends ChapterSaveDto {
    private String comicName;
}

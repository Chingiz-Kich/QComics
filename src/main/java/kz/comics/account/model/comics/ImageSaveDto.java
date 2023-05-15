package kz.comics.account.model.comics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageSaveDto {
    private String name;
    private String comicName;
    private String chapterName;
    private String base64;
}

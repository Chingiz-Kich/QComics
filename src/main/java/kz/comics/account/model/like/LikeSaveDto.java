package kz.comics.account.model.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeSaveDto {

    private Integer userId;
    private Integer comicId;
}

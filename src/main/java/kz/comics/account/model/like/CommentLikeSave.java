package kz.comics.account.model.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeSave {

    private Integer commentId;
    private Integer userId;
}

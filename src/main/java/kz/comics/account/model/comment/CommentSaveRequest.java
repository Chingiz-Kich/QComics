package kz.comics.account.model.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.comics.account.model.like.CommentLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequest {

    private int chapterId;
    private int userId;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate createdDate;

    private CommentLikeType commentLikeType;
}

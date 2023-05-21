package kz.comics.account.model.readingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingStatusRequest {
    private String username;
    private Integer chapterId;
}

package kz.comics.account.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {

    private String field;
    private Boolean ascending;
    private int page;
    private int size;
}

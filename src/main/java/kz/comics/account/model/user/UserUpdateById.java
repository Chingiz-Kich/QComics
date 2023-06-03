package kz.comics.account.model.user;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateById extends UserUpdateRequest {
    private Integer id;
}

package kz.comics.account.model.user;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateByUsername extends UserUpdateRequest{
    private String username;
}

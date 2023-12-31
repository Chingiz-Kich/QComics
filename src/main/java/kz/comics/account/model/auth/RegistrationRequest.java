package kz.comics.account.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    /**
     * (Id) username для логина
     */
    private String username;

    /**
     * Пароль
     */
    private String password;

    /**
     * Почта
     */
    private String email;
}

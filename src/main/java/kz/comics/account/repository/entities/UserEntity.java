package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import kz.comics.account.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity  {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    // @Enumerated - to tell Spring Boot that is enum
    // By default it is EnumType.ORDINAL: 0, 1, 2
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comics_id", referencedColumnName = "id")
    private List<ComicsEntity> comics;
}

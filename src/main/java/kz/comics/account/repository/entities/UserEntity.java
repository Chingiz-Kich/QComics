package kz.comics.account.repository.entities;

import jakarta.persistence.*;
import kz.comics.account.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

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

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    /**
     *  @OneToMany(cascade = CascadeType.ALL)
     *
     *  The cascade = CascadeType.ALL option means that any operations performed on the UserEntity (such as saving or updating) will cascade to the associated ComicsEntity instances.
     *  This includes generating new IDs for the ComicsEntity instances when they are added to the comics collection.
     *
     *  By removing the cascade = CascadeType.ALL, you retain control over the IDs of the ComicsEntity instances when associating them with a UserEntity.
     *  You'll need to ensure that the ComicsEntity instances have valid IDs assigned before adding them to the UserEntity and saving the BookmarkEntity.
     *
     * Additionally, make sure that the id field of ComicsEntity is properly annotated with @GeneratedValue to ensure automatic ID generation when persisting new ComicsEntity instances.
     */
    @OneToMany
    @JoinColumn(name = "comics_id", referencedColumnName = "id")
    private List<ComicsEntity> comics;

    @ManyToMany
    @JoinTable(
            name = "user_bookmarks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id")
    )
    private List<BookmarkEntity> bookmarks;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> subscriptions;

    @ManyToMany(mappedBy = "subscriptions")
    private List<UserEntity> subscribers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

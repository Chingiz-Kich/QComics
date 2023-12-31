package kz.comics.account.repository;

import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserByUsername(String username);

    Optional<UserEntity> deleteByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}

package kz.comics.account.repository;

import kz.comics.account.repository.entities.ComicEntity;
import kz.comics.account.repository.entities.RateEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Integer> {
    Optional<RateEntity> findRateEntityByComicsAndAndUser(ComicEntity comicEntity, UserEntity userEntity);
}

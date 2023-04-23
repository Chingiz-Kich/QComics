package kz.comics.account.repository;

import kz.comics.account.model.comics.ComicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicsRepository extends JpaRepository<ComicsEntity, Integer> {

    Optional<ComicsEntity> getComicsEntitiesByName(String name);

}
package kz.comics.account.repository;

import kz.comics.account.repository.entities.ComicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicsRepository extends JpaRepository<ComicEntity, Integer> {

    Optional<ComicEntity> getComicsEntitiesByName(String name);
    Optional<ComicEntity> deleteByName(String name);
    Page<ComicEntity> findAll(Specification<ComicEntity> spec, Pageable pageable);

}

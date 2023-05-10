package kz.comics.account.repository;

import kz.comics.account.repository.entities.ComicsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicsRepository extends JpaRepository<ComicsEntity, Integer> {

    Optional<ComicsEntity> getComicsEntitiesByName(String name);
    Optional<ComicsEntity> deleteByName(String name);
    Page<ComicsEntity> findAll(Specification<ComicsEntity> spec, Pageable pageable);

}

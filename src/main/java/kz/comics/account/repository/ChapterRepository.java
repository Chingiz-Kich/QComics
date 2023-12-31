package kz.comics.account.repository;

import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<ChapterEntity, Integer> {
    Optional<ChapterEntity> getByName(String name);
    Optional<List<ChapterEntity>> findAllByComicsEntity(ComicsEntity comicsEntity);
}

package kz.comics.account.repository;

import kz.comics.account.repository.entities.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<ChapterEntity, Integer> {
}

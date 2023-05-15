package kz.comics.account.repository;

import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    ImageEntity getImageEntityById(Integer id);
    List<ImageEntity> getAllByChapterEntityAndComicsEntity(ChapterEntity chapterEntity, ComicsEntity comicsEntity);
}

package kz.comics.account.repository;

import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByChapterEntity(ChapterEntity chapterEntity);
}

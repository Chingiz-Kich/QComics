package kz.comics.account.repository;

import kz.comics.account.model.comics.ImageCoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageCoverRepository extends JpaRepository<ImageCoverEntity, Integer> {
}

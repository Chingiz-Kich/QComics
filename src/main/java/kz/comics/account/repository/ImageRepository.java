package kz.comics.account.repository;

import kz.comics.account.model.comics.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}

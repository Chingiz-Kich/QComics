package kz.comics.account.repository;

import kz.comics.account.repository.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> getImageEntitiesByName(String name);
    ImageEntity getImageEntityById(Integer id);
}

package kz.comics.account.service;

import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.repository.entities.ImageEntity;

import java.util.List;

public interface ImageService {
    ImageDto save(ImageDto imageDto);
    List<ImageDto> saveAll(List<ImageDto> imageDtoList);
    List<ImageDto> getAllByName(String name);

    // This method only for downloading
    ImageEntity getImageEntityById(Integer id);

    List<Integer> getListIdByName(String name);
    ImageDto getImageById(Integer id);
}

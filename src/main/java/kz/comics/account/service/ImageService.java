package kz.comics.account.service;

import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
import kz.comics.account.repository.entities.ImageEntity;

import java.util.List;

public interface ImageService {
    ImageDto save(ImageSaveDto imageSaveDto);
    List<Integer> saveAll(List<ImageSaveDto> imageSaveDtos);

    // This method only for downloading
    ImageEntity downloadById(Integer id);
    List<ImageEntity> downloadAll( String chapterName, String comicName);

    ImageDto getById(Integer id);
    String deleteAll();
    List<ImageDto> getAllByChapterNameAndComicName(String chapterName, String comicName);
    List<Integer> getAllIdsByChapterAndComicName(String chapterName, String comicName);
    String cacheEvict(String key);
}

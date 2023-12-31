package kz.comics.account.mapper;

import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.entities.ImageEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class ImageMapper {

    private final ComicsRepository comicsRepository;
    private final ChapterRepository chapterRepository;

    public ImageEntity toEntity(ImageSaveDto imageSaveDto) {
        return ImageEntity.builder()
                .comicsEntity(comicsRepository.getComicsEntitiesByName(imageSaveDto.getComicName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot fina comics with name: %s", imageSaveDto.getComicName()))))
                .chapterEntity(chapterRepository.getByName(imageSaveDto.getChapterName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with name: %s", imageSaveDto.getChapterName()))))
                .data(Base64.getDecoder().decode(imageSaveDto.getBase64()))
                .build();
    }

    public ImageEntity toEntity(ImageDto imageDto) {
        return ImageEntity.builder()
                .id(imageDto.getId())
                .comicsEntity(comicsRepository.getComicsEntitiesByName(imageDto.getComicName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot fina comics with name: %s", imageDto.getComicName()))))
                .chapterEntity(chapterRepository.getByName(imageDto.getChapterName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with name: %s", imageDto.getChapterName()))))
                .data(Base64.getDecoder().decode(imageDto.getBase64()))
                .build();
    }

    public ImageDto toDto(ImageEntity imageEntity) {
        return ImageDto.builder()
                .id(imageEntity.getId())
                .comicName(imageEntity.getComicsEntity().getName())
                .chapterName(imageEntity.getChapterEntity().getName())
                .base64(Base64.getEncoder().encodeToString(imageEntity.getData()))
                .build();
    }

    public List<ImageDto> toImageDtoList(List<ImageEntity> imageEntityList) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        imageEntityList.forEach(imageEntity -> imageDtoList.add(this.toDto(imageEntity)));
        return imageDtoList;
    }

    public List<ImageEntity> toImageEntityList(List<ImageSaveDto> imageSaveDtos) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        imageSaveDtos.forEach(imageDto -> imageEntityList.add(this.toEntity(imageDto)));
        return imageEntityList;
    }
}

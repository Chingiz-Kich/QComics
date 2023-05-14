package kz.comics.account.mapper;

import kz.comics.account.model.comics.ImageDto;
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

    public ImageEntity toEntity(ImageDto imageDto) {
        return ImageEntity.builder()
                .id(imageDto.getId())
                .name(imageDto.getName())
                .comics(comicsRepository.getComicsEntitiesByName(imageDto.getComicName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot fina comics with name: %s", imageDto.getComicName()))))
                .chapter(chapterRepository.getByName(imageDto.getChapterName())
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with name: %s", imageDto.getChapterName()))))
                .data(Base64.getDecoder().decode(imageDto.getBase64()))
                .build();
    }

    public ImageDto toDto(ImageEntity imageEntity) {
        return ImageDto.builder()
                .id(imageEntity.getId())
                .name(imageEntity.getName())
                .comicName(imageEntity.getComics().getName())
                .chapterName(imageEntity.getChapter().getName())
                .base64(Base64.getEncoder().encodeToString(imageEntity.getData()))
                .build();
    }

    public List<ImageDto> toImageDtoList(List<ImageEntity> imageEntityList) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        imageEntityList.forEach(imageEntity -> imageDtoList.add(this.toDto(imageEntity)));
        return imageDtoList;
    }

    public List<ImageEntity> toImageEntityList(List<ImageDto> imageDtoList) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        imageDtoList.forEach(imageDto -> imageEntityList.add(this.toEntity(imageDto)));
        return imageEntityList;
    }
}

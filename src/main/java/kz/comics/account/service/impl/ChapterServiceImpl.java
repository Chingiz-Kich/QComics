package kz.comics.account.service.impl;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ImageRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ImageEntity;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ImageRepository imageRepository;
    private final ChapterRepository chapterRepository;

    @Override
    public ChapterDto save(ChapterSaveDto chapterSaveDto) {
        log.info("Saving chapterDto name: {}", chapterSaveDto.getName());

        List<ImageEntity> imageEntityList = imageRepository.findAllById(chapterSaveDto.getImageIds());

        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .name(chapterSaveDto.getName())
                .images(imageEntityList)
                .build();

        chapterEntity = chapterRepository.save(chapterEntity);

        log.info("Saved chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .imageIds(chapterEntity.getImages()
                        .stream()
                        .map(ImageEntity::getId)
                        .toList())
                .build();
    }

    @Override
    public ChapterDto getById(Integer id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No chapter"));

        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .imageIds(chapterEntity.getImages()
                        .stream()
                        .map(ImageEntity::getId)
                        .toList())
                .build();
    }
}

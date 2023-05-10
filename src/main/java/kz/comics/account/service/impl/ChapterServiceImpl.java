package kz.comics.account.service.impl;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
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

    private final ChapterRepository chapterRepository;
    private final ComicsRepository comicsRepository;

    @Override
    public ChapterDto save(ChapterSaveDto chapterSaveDto) {
        log.info("Saving chapterDto name: {}", chapterSaveDto.getName());

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(chapterSaveDto.getComicName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", chapterSaveDto.getComicName())));

        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .name(chapterSaveDto.getName())
                .comics(comicsEntity)
                .build();

        chapterEntity = chapterRepository.save(chapterEntity);

        log.info("Saved chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(comicsEntity.getName())
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
                .comicName(chapterEntity.getComics().getName())
                .build();
    }

    @Override
    public List<ChapterDto> getAll() {
        List<ChapterEntity> chapterEntities = chapterRepository.findAll();

        return chapterEntities.stream()
                .map(chapterEntity -> ChapterDto
                        .builder()
                        .id(chapterEntity.getId())
                        .name(chapterEntity.getName())
                        .comicName(chapterEntity.getComics().getName())
                        .build())
                .toList();
    }
}

package kz.comics.account.service.impl;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.model.comics.ChapterUpdate;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ComicsRepository comicsRepository;

    @Override
    @Transactional
    public ChapterDto save(ChapterSaveDto chapterSaveDto) {
        log.info("Saving ChapterSaveDto name: {}", chapterSaveDto.getName());

        if (chapterRepository.getByName(chapterSaveDto.getName()).isPresent()) {
            throw new IllegalStateException(String.format("Chapter with name: %s already exist!", chapterSaveDto.getName()));
        }

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(chapterSaveDto.getComicName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", chapterSaveDto.getComicName())));

        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .name(chapterSaveDto.getName())
                .comicsEntity(comicsEntity)
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
    @Transactional
    public ChapterDto getById(Integer id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No chapter with id: %s", id)));

        ComicsEntity comicsEntity = chapterEntity.getComicsEntity();

        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(comicsEntity.getName())
                .build();
    }

    @Override
    @Transactional
    public List<ChapterDto> getAll() {
        List<ChapterEntity> chapterEntities = chapterRepository.findAll();

        return chapterEntities.stream()
                .map(chapterEntity -> ChapterDto
                        .builder()
                        .id(chapterEntity.getId())
                        .name(chapterEntity.getName())
                        .comicName(chapterEntity.getComicsEntity().getName())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public List<ChapterDto> getByComicName(String comicName) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        List<ChapterEntity> chapterEntities = chapterRepository.findAllByComicsEntity(comicsEntity)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapters with comic id: %s", comicsEntity.getId())));

        return chapterEntities.stream()
                .map(chapterEntity -> ChapterDto
                        .builder()
                        .id(chapterEntity.getId())
                        .name(chapterEntity.getName())
                        .comicName(comicName)
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public String deleteById(Integer id) {
        chapterRepository.deleteById(id);
        return "Chapter with id: " + id + " successfully deleted";
    }

    @Override
    @Transactional
    public ChapterDto update(ChapterUpdate chapterUpdate) {
        log.info("Updating ChapterUpdate name: {}", chapterUpdate.getName());

        ChapterEntity chapter = chapterRepository.findById(chapterUpdate.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterUpdate.getId())));;

        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .id(chapterUpdate.getId())
                .name(chapterUpdate.getName())
                .comicsEntity(chapter.getComicsEntity())
                .build();

        chapterEntity = chapterRepository.save(chapterEntity);

        log.info("Updated chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(chapterEntity.getComicsEntity().getName())
                .build();
    }

    @Override
    @Transactional
    public String deleteAll() {
        chapterRepository.deleteAll();
        return "All chapters deleted";
    }
}

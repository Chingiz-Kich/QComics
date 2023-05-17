package kz.comics.account.service.impl;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.model.comics.ChapterUpdate;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicEntity;
import kz.comics.account.service.ChapterService;
import kz.comics.account.service.ImageService;
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
    private final ImageService imageService;

    @Override
    @Transactional
    public ChapterDto save(ChapterSaveDto chapterSaveDto) {
        log.info("Saving ChapterSaveDto name: {}", chapterSaveDto.getName());

        if (chapterRepository.getByName(chapterSaveDto.getName()).isPresent()) {
            throw new IllegalStateException(String.format("Chapter with name: %s already exist!", chapterSaveDto.getName()));
        }

        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(chapterSaveDto.getComicName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", chapterSaveDto.getComicName())));

        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .name(chapterSaveDto.getName())
                .comicEntity(comicEntity)
                .build();

        chapterEntity = chapterRepository.save(chapterEntity);

        log.info("Saved chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(comicEntity.getName())
                .build();
    }

    @Override
    @Transactional
    public ChapterDto getById(Integer id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No chapter with id: %s", id)));

        ComicEntity comicEntity = chapterEntity.getComicEntity();

        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(comicEntity.getName())
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
                        .comicName(chapterEntity.getComicEntity().getName())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public List<ChapterDto> getByComicName(String comicName) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        List<ChapterEntity> chapterEntities = chapterRepository.findAllByComicsEntity(comicEntity)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapters with comic id: %s", comicEntity.getId())));

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
        ChapterEntity chapterEntity = chapterRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", id)));

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
                .comicEntity(chapter.getComicEntity())
                .build();

        chapterEntity = chapterRepository.save(chapterEntity);

        log.info("Updated chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return ChapterDto
                .builder()
                .id(chapterEntity.getId())
                .name(chapterEntity.getName())
                .comicName(chapterEntity.getComicEntity().getName())
                .build();
    }

    @Override
    @Transactional
    public String deleteAll() {
        chapterRepository.deleteAll();
        return "All chapters deleted";
    }

    @Override
    public void deleteAllByComicEntity(ComicEntity comicEntity) {
        chapterRepository.findAllByComicsEntity(comicEntity)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with comicName: %s", comicEntity.getName())))
                .forEach(chapterEntity -> imageService.deleteAllByChapterAndComicEntity(chapterEntity, comicEntity));

        chapterRepository.deleteAllByComicsEntity(comicEntity);
    }
}

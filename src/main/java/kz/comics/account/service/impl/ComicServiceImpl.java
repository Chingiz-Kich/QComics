package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.ImageCoverMapper;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.model.comics.ComicSaveDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.ImageCoverRepository;
import kz.comics.account.service.ComicService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicsRepository comicsRepository;
    private final ChapterRepository chapterRepository;
    private final ImageCoverRepository imageCoverRepository;
    private final ImageCoverMapper imageCoverMapper;
    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public ComicDto saveComic(ComicSaveDto comicSaveDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicSaveDto));

        ComicsEntity comicsEntity = ComicsEntity
                .builder()
                .name(comicSaveDto.getName())
                .author(comicSaveDto.getAuthor())
                .description(comicSaveDto.getDescription())
                .coverImage((Base64.getDecoder().decode(comicSaveDto.getImageCoverBase64())))
                .genres(new LinkedHashSet<>(comicSaveDto.getGenres()))
                .build();

        comicsEntity = comicsRepository.save(comicsEntity);
        log.info("Saved comics: {}", objectMapper.writeValueAsString(comicsEntity));

        return this.entityToDto(comicsEntity);
    }

    @Override
    @SneakyThrows
    public ComicDto getComic(String comicName) {
        log.info("Get comics name: {}", comicName);

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Comics name %s not found", comicName)));

        log.info("Got comics from repository: {}", objectMapper.writeValueAsString(comicsEntity));
        return this.entityToDto(comicsEntity);
    }

    @Override
    public List<ComicDto> getAll() {
        List<ComicsEntity> comicsEntities = comicsRepository.findAll();

        return comicsEntities.stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public ComicDto updateComic(ComicDto comicDto) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicDto.getName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Where is fucking comics? Name: %s", comicDto.getName())));

        comicsEntity = this.dtoToEntity(comicDto, true);
        return this.entityToDto(comicsEntity);
    }


    // FIXME: This shit should be in ComicsMapper !!!!
    private ComicDto entityToDto(ComicsEntity comicsEntity) {
        return ComicDto
                .builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                .imageCoverBase64(Base64.getEncoder().encodeToString(comicsEntity.getCoverImage()))
                .chapterIds(comicsEntity.getChapters()
                        .stream()
                        .map(ChapterEntity::getId)
                        .toList())
                .rating(comicsEntity.getRating())
                .rates(comicsEntity.getRates())
                .description(comicsEntity.getDescription())
                .type(comicsEntity.getType())
                .publishedDate(comicsEntity.getPublishedDate())
                .isUpdated(comicsEntity.getIsUpdated())
                .build();
    }

    // FIXME: This shit should be in ComicsMapper !!!!
    private ComicsEntity dtoToEntity(ComicDto comicDto, Boolean isUpdate) {
        return ComicsEntity
                .builder()
                .name(comicDto.getName())
                .author(comicDto.getAuthor())
                .genres(new LinkedHashSet<>(comicDto.getGenres()))
                .coverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())))
                .chapters(comicDto.getChapterIds()
                        .stream()
                        .map(id -> chapterRepository.findById(id).
                                orElseThrow(() -> new NoSuchElementException(String.format("Chapter with id: %s not found!", id))))
                        .toList())
                .rating(comicDto.getRating())
                .rates(comicDto.getRates())
                .description(comicDto.getDescription())
                .type(comicDto.getType())
                .publishedDate(comicDto.getPublishedDate())
                .isUpdated(isUpdate)
                .build();
    }
}

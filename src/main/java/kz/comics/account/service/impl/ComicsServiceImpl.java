package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.ChapterMapper;
import kz.comics.account.mapper.ComicsMapper;
import kz.comics.account.mapper.ImageCoverMapper;
import kz.comics.account.model.comics.ComicsDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.ImageCoverEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.ImageCoverRepository;
import kz.comics.account.service.ComicsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicsServiceImpl implements ComicsService {

    private final ComicsRepository comicsRepository;
    private final ChapterRepository chapterRepository;
    private final ImageCoverRepository imageCoverRepository;
    private final ComicsMapper comicsMapper;
    private final ChapterMapper chapterMapper;
    private final ImageCoverMapper imageCoverMapper;
    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public ComicsDto saveComics(ComicsDto comicsDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicsDto));

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicsDto.getName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot fina comics with name: %s", comicsDto.getName())));

        List<ChapterEntity> chapterEntities = chapterRepository.saveAll(chapterMapper.toChapterEntityList(comicsDto.getChapters(), comicsEntity));
        ImageCoverEntity imageCoverEntity = imageCoverRepository.save(imageCoverMapper.toEntity(comicsDto.getCover()));

        ComicsEntity check = comicsMapper.toEntity(comicsDto);
        check.setChapters(chapterEntities);
        check.setCover(imageCoverEntity);

        ComicsEntity savedComicsEntity = comicsRepository.save(check);

        log.info("Saved comics: {}", objectMapper.writeValueAsString(savedComicsEntity));
        return comicsMapper.toDto(savedComicsEntity);
    }

    @Override
    @SneakyThrows
    public ComicsDto getComics(String comicsName) {
        log.info("Get comics name: {}", comicsName);

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicsName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Comics name %s not found", comicsName)));

        log.info("Got comics from repository: {}", objectMapper.writeValueAsString(comicsEntity));
        return comicsMapper.toDto(comicsEntity);
    }
}

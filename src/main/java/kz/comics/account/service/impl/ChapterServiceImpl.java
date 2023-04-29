package kz.comics.account.service.impl;

import kz.comics.account.mapper.ChapterMapper;
import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ComicsRepository comicsRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterDto save(ChapterDto chapterDto) {
        log.info("Saving chapterDto name: {}", chapterDto.getName());

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(chapterDto.getComicsName())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comics with name: %s", chapterDto.getComicsName())));

        ChapterEntity chapterEntity = chapterRepository.save(chapterMapper.toEntity(chapterDto, comicsEntity));
        log.info("Saved chapterDto name: {}, id: {}", chapterEntity.getName(), chapterEntity.getId());
        return chapterMapper.toDto(chapterEntity);
    }
}

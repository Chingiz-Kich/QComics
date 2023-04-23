package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.ComicsMapper;
import kz.comics.account.mapper.ImageMapper;
import kz.comics.account.model.comics.ComicsDto;
import kz.comics.account.model.comics.ComicsEntity;
import kz.comics.account.model.comics.ImageCoverEntity;
import kz.comics.account.model.comics.ImageEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.ImageCoverRepository;
import kz.comics.account.repository.ImageRepository;
import kz.comics.account.service.ComicsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicsServiceImpl implements ComicsService {

    private final ComicsRepository comicsRepository;
    private final ImageRepository imageRepository;
    private final ImageCoverRepository imageCoverRepository;
    private final ComicsMapper comicsMapper;
    private final ImageMapper imageMapper;
    private final ObjectMapper objectMapper;


    // TODO: Вот этот гребаный метод еще надо доработать!!!!
    @Override
    @SneakyThrows
    public ComicsDto saveComics(ComicsDto comicsDto) {
        //log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicsDto));
        List<ImageEntity> imageEntity = imageRepository.saveAll(imageMapper.toImageEntityList(comicsDto.getChapters()));
        ImageCoverEntity imageCoverEntity = imageCoverRepository.save(comicsDto.getCover());
        ComicsEntity check = comicsMapper.toEntity(comicsDto);
        check.setChapters(imageEntity);
        check.setCover(imageCoverEntity);
        ComicsEntity comicsEntity = comicsRepository.save(check);
        //log.info("Saved comics: {}", objectMapper.writeValueAsString(comicsEntity));
        return comicsMapper.toDto(comicsEntity);
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

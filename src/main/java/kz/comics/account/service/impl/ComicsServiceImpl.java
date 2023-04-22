package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.ComicsMapper;
import kz.comics.account.model.comics.ComicsDto;
import kz.comics.account.model.comics.ComicsEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.service.ComicsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicsServiceImpl implements ComicsService {

    private final ComicsRepository comicsRepository;
    private final ComicsMapper comicsMapper;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public ComicsDto saveComics(ComicsDto comicsDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicsDto));
        ComicsEntity comicsEntity = comicsRepository.save(comicsMapper.toEntity(comicsDto));
        log.info("Saved comics: {}", objectMapper.writeValueAsString(comicsEntity));
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

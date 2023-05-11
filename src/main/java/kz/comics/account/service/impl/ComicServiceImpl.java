package kz.comics.account.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.jdi.request.InvalidRequestStateException;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.model.comics.ComicsType;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.service.ComicService;
import kz.comics.account.util.ComicSpecification;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicsRepository comicsRepository;
    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public ComicDto saveComic(ComicDto comicDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicDto));

        Optional<ComicsEntity> comics = comicsRepository.getComicsEntitiesByName(comicDto.getName());
        if (comics.isPresent()) throw new IllegalStateException(String.format("Comics with name: %s already exist", comicDto.getName()));

        ComicsEntity comicsEntity = this.dtoToEntity(comicDto);

        comicsEntity = comicsRepository.save(comicsEntity);
        log.info("Saved comics: {}", objectMapper.writeValueAsString(comicsEntity));

        return this.entityToDto(comicsEntity);
    }

    @Override
    @SneakyThrows
    @Transactional
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
    @Transactional
    public ComicDto updateComic(ComicDto comicDto) {

        Optional<ComicsEntity> comicsEntityOptional = comicsRepository.getComicsEntitiesByName(comicDto.getName());
        if (comicsEntityOptional.isEmpty()) throw new IllegalStateException(String.format("Comics with name: %s does not exist", comicDto.getName()));

        ComicsEntity comicsEntity = comicsEntityOptional.get();

        if (StringUtils.isNotBlank(comicDto.getAuthor())) {
            comicsEntity.setAuthor(comicDto.getAuthor());
        }

        if (!comicDto.getGenres().isEmpty()) {
            comicsEntity.setGenres(new LinkedHashSet<>(comicDto.getGenres()));
        }

        if (StringUtils.isNotBlank(comicDto.getImageCoverBase64())) {
            comicsEntity.setCoverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())));
        }

        if (StringUtils.isNotBlank(comicDto.getDescription())) {
            comicsEntity.setDescription(comicDto.getDescription());
        }

        if (comicDto.getRating() != null) {
            comicsEntity.setRating(comicDto.getRating());
        }

        if (comicDto.getRates() != null) {
            comicsEntity.setRates(comicDto.getRates());
        }

        if (comicDto.getType() != null) {
            comicsEntity.setType(comicDto.getType());
        }

        if (comicDto.getPublishedDate() != null) {
            comicsEntity.setPublishedDate(comicDto.getPublishedDate());
        }

        comicsEntity.setIsUpdated(true);

        comicsRepository.save(comicsEntity);

        return this.entityToDto(comicsEntity);
    }

    @Override
    @Transactional
    public ComicDto delete(String name) {
        comicsRepository.deleteByName(name)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot delete comic with name: %s", name)));

        return new ComicDto();
    }

    @Override
    public String deleteAll() {
        comicsRepository.deleteAll();
        return "easy peasy lemon squeezy";
    }

    @Override
    public List<ComicDto> findAll(String field, Boolean ascending, int page, int size) {
        Pageable pageable;
        if (ascending) pageable = PageRequest.of(page, size, Sort.by(field).ascending());
        else pageable = PageRequest.of(page, size, Sort.by(field).descending());

        Page<ComicsEntity> comicsEntities;
        if (ascending) comicsEntities = comicsRepository.findAll(ComicSpecification.orderByAsc(field), pageable);
        else comicsEntities = comicsRepository.findAll(ComicSpecification.orderByDesc(field), pageable);


        return comicsEntities
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    @SneakyThrows
    public List<ComicDto> findMapAll(Map<String, Object> filters, Pageable pageable) {
        Specification<ComicsEntity> spec = Specification.where(null);

        for (String key : filters.keySet()) {
            Object value = filters.get(key);
            if (value != null) {
                String stringValue = value.toString();
                if (key.equals("type")) {
                    try {
                        ComicsType comicsType = objectMapper.readValue('"' + stringValue + '"', ComicsType.class);
                        spec = spec.and((root, query, builder) -> builder.equal(root.get(key), comicsType));
                    } catch (JsonProcessingException e) {
                        throw new InvalidRequestStateException("Invalid filter value for key " + key);
                    }
                } else {
                    spec = spec.and((root, query, builder) -> builder.equal(root.get(key), value));
                }
            }
        }

        Page<ComicsEntity> comicsEntities = comicsRepository.findAll(spec, pageable);

        return comicsEntities
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    // FIXME: This shit should be in ComicsMapper !!!!
    private ComicDto entityToDto(ComicsEntity comicsEntity) {
        return ComicDto
                .builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                .imageCoverBase64(Base64.getEncoder().encodeToString(comicsEntity.getCoverImage()))
                .rating(comicsEntity.getRating())
                .rates(comicsEntity.getRates())
                .description(comicsEntity.getDescription())
                .type(comicsEntity.getType())
                .publishedDate(comicsEntity.getPublishedDate())
                .isUpdated(comicsEntity.getIsUpdated())
                .build();
    }

    // FIXME: This shit should be in ComicsMapper !!!!
    private ComicsEntity dtoToEntity(ComicDto comicDto) {
        return ComicsEntity
                .builder()
                .name(comicDto.getName())
                .author(comicDto.getAuthor())
                .genres(new LinkedHashSet<>(comicDto.getGenres()))
                .coverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())))
                .rating(comicDto.getRating())
                .rates(comicDto.getRates())
                .description(comicDto.getDescription())
                .type(comicDto.getType())
                .publishedDate(comicDto.getPublishedDate())
                .isUpdated(false)
                .build();
    }
}

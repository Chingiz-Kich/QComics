package kz.comics.account.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.jdi.request.InvalidRequestStateException;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.model.comics.ComicsType;
import kz.comics.account.repository.entities.ComicEntity;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.service.ChapterService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicsRepository comicsRepository;
    private final ObjectMapper objectMapper;
    private final ChapterService chapterService;

    @Override
    @SneakyThrows
    public ComicDto saveComic(ComicDto comicDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicDto));

        if (!comicDto.getImageCoverBase64().isBlank() && comicDto.getImageCoverBase64().startsWith("data:")) {
            String base64 = comicDto.getImageCoverBase64();
            int indexStart = base64.indexOf(",") + 1;
            String modifiedBase64 = base64.substring(indexStart, base64.length());
            comicDto.setImageCoverBase64(modifiedBase64);
        }

        Optional<ComicEntity> comics = comicsRepository.getComicsEntitiesByName(comicDto.getName());
        if (comics.isPresent()) throw new IllegalStateException(String.format("Comics with name: %s already exist", comicDto.getName()));

        ComicEntity comicEntity = this.dtoToEntity(comicDto);

        comicEntity = comicsRepository.save(comicEntity);
        log.info("Saved comics: {}", objectMapper.writeValueAsString(comicEntity));

        return this.entityToDto(comicEntity);
    }

    @Override
    @SneakyThrows
    @Transactional
    public ComicDto getComic(String comicName) {
        log.info("Get comics name: {}", comicName);

        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        log.info("Got comics from repository: {}", objectMapper.writeValueAsString(comicEntity));
        return this.entityToDto(comicEntity);
    }

    @Override
    public List<ComicDto> getAll() {
        List<ComicEntity> comicsEntities = comicsRepository.findAll();

        return comicsEntities.stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public ComicDto updateComic(ComicDto comicDto) {

        Optional<ComicEntity> comicsEntityOptional = comicsRepository.getComicsEntitiesByName(comicDto.getName());
        if (comicsEntityOptional.isEmpty()) throw new IllegalStateException(String.format("Comics with name: %s does not exist", comicDto.getName()));

        ComicEntity comicEntity = comicsEntityOptional.get();

        if (StringUtils.isNotBlank(comicDto.getAuthor())) {
            comicEntity.setAuthor(comicDto.getAuthor());
        }

        if (!comicDto.getGenres().isEmpty()) {
            comicEntity.setGenres(new LinkedHashSet<>(comicDto.getGenres()));
        }

        if (StringUtils.isNotBlank(comicDto.getImageCoverBase64())) {
            comicEntity.setCoverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())));
        }

        if (StringUtils.isNotBlank(comicDto.getDescription())) {
            comicEntity.setDescription(comicDto.getDescription());
        }

        if (comicDto.getRating() != null) {
            comicEntity.setRating(comicDto.getRating());
        }

/*
        if (comicDto.getRates() != null) {
            comicsEntity.setRates(comicDto.getRates());
        }
*/

        if (comicDto.getType() != null) {
            comicEntity.setType(comicDto.getType());
        }

        if (comicDto.getPublishedDate() != null) {
            comicEntity.setPublishedDate(comicDto.getPublishedDate());
        }

        comicEntity.setIsUpdated(true);

        comicsRepository.save(comicEntity);

        return this.entityToDto(comicEntity);
    }

    @Override
    @Transactional
    public ComicDto delete(String name) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(name)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", name)));

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

        Page<ComicEntity> comicsEntities;
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
        Specification<ComicEntity> spec = Specification.where(null);

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

        Page<ComicEntity> comicsEntities = comicsRepository.findAll(spec, pageable);

        List<ComicDto> comicDtos =  comicsEntities
                .stream()
                .map(this::entityToDto)
                .toList();

        List<ComicDto> reversedComicDtos = new ArrayList<>(comicDtos);
        Collections.reverse(reversedComicDtos);

        return reversedComicDtos;
    }

    @Override
    @Transactional
    public void upVotes(String comicName) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double votes = comicEntity.getVotes();
        votes++;
        comicEntity.setVotes(votes);
        comicsRepository.save(comicEntity);
    }

    @Override
    @Transactional
    public void downVotes(String comicName) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double votes = comicEntity.getVotes();
        votes--;
        comicEntity.setVotes(votes);
        comicsRepository.save(comicEntity);
    }

    @Override
    @Transactional
    public void updateRating(String comicName, double rate) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double rating = comicEntity.getRating();
        rating += rate;

        double votes = comicEntity.getVotes();
        rating /= votes;

        comicEntity.setRating(rating);
        comicsRepository.save(comicEntity);
    }

    @Override
    public void deleteByComicEntity(ComicEntity comicEntity) {
        chapterService.deleteAllByComicEntity(comicEntity);
    }


    // FIXME: This shit should be in ComicsMapper !!!!
    public ComicDto entityToDto(ComicEntity comicEntity) {
        return ComicDto
                .builder()
                .name(comicEntity.getName())
                .author(comicEntity.getAuthor())
                .genres(comicEntity.getGenres().stream().toList())
                .imageCoverBase64(Base64.getEncoder().encodeToString(comicEntity.getCoverImage()))
                .rating(comicEntity.getRating())
                .votes(comicEntity.getVotes())
                .description(comicEntity.getDescription())
                .type(comicEntity.getType())
                .publishedDate(comicEntity.getPublishedDate())
                .isUpdated(comicEntity.getIsUpdated())
                .build();
    }

    // FIXME: This shit should be in ComicsMapper !!!!
    public ComicEntity dtoToEntity(ComicDto comicDto) {
        return ComicEntity
                .builder()
                .name(comicDto.getName())
                .author(comicDto.getAuthor())
                .genres(new LinkedHashSet<>(comicDto.getGenres()))
                .coverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())))
                .rating(comicDto.getRating())
                .votes(comicDto.getVotes())
                .description(comicDto.getDescription())
                .type(comicDto.getType())
                .publishedDate(comicDto.getPublishedDate())
                .isUpdated(false)
                .build();
    }
}

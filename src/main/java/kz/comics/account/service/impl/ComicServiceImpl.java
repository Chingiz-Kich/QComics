package kz.comics.account.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.jdi.request.InvalidRequestStateException;
import kz.comics.account.mapper.ComicsMapper;
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
import org.springframework.cache.annotation.Cacheable;
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
@Transactional
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicsRepository comicsRepository;
    private final ObjectMapper objectMapper;
    private final ComicsMapper comicsMapper;


    @Override
    @SneakyThrows
    public ComicsEntity saveComic(ComicDto comicDto) {
        log.info("Get comics to save: {}", objectMapper.writeValueAsString(comicDto));

        if (!comicDto.getImageCoverBase64().isBlank() && comicDto.getImageCoverBase64().startsWith("data:")) {
            String base64 = comicDto.getImageCoverBase64();
            int indexStart = base64.indexOf(",") + 1;
            String modifiedBase64 = base64.substring(indexStart, base64.length());
            comicDto.setImageCoverBase64(modifiedBase64);
        }

        Optional<ComicsEntity> comics = comicsRepository.getComicsEntitiesByName(comicDto.getName());
        if (comics.isPresent()) throw new IllegalStateException(String.format("Comics with name: %s already exist", comicDto.getName()));

        ComicsEntity comicsEntity = comicsMapper.dtoToEntity(comicDto);

        comicsEntity = comicsRepository.save(comicsEntity);
        log.info("Saved comics: {}", objectMapper.writeValueAsString(comicsEntity));

        return comicsEntity;
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "comic", key = "#comicName")
    public ComicsEntity getByName(String comicName) {
        log.info("Get comics name: {}", comicName);

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        log.info("Got comics from repository: {}", objectMapper.writeValueAsString(comicsEntity));
        return comicsEntity;
    }

    @Override
    public ComicsEntity getById(Integer id) {
        return comicsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic by id: %s", id)));
    }

    @Override
    @Cacheable(value = "comics")
    public List<ComicsEntity> getAll() {
        return comicsRepository.findAll();
    }

    @Override
    public ComicsEntity updateComic(ComicDto comicDto) {

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

        if (comicDto.getRating() != 0) {
            comicsEntity.setRating(comicDto.getRating());
        }

        if (comicDto.getType() != null) {
            comicsEntity.setType(comicDto.getType());
        }

        if (comicDto.getPublishedDate() != null) {
            comicsEntity.setPublishedDate(comicDto.getPublishedDate());
        }

        comicsEntity.setIsUpdated(true);

        return comicsRepository.save(comicsEntity);
    }

    @Override
    public String delete(String name) {
        comicsRepository.deleteByName(name)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot delete comic with name: %s", name)));

        return name + " deleted";
    }

    @Override
    public String deleteAll() {
        comicsRepository.deleteAll();
        return "easy peasy lemon squeezy";
    }

    @Override
    public List<ComicsEntity> findAll(String field, Boolean ascending, int page, int size) {
        Pageable pageable;
        if (ascending) pageable = PageRequest.of(page, size, Sort.by(field).ascending());
        else pageable = PageRequest.of(page, size, Sort.by(field).descending());

        Page<ComicsEntity> comicsEntities;
        if (ascending) comicsEntities = comicsRepository.findAll(ComicSpecification.orderByAsc(field), pageable);
        else comicsEntities = comicsRepository.findAll(ComicSpecification.orderByDesc(field), pageable);


        return comicsEntities.stream().toList();
    }

    @Override
    @SneakyThrows
    public List<ComicsEntity> findMapAll(Map<String, Object> filters, Pageable pageable) {
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

/*        List<ComicDto> comicDtos =  comicsEntities
                .stream()
                .map(this::entityToDto)
                .toList();*/

        List<ComicsEntity> reversedComicDtos = new ArrayList<>(comicsEntities.stream().toList());
        Collections.reverse(reversedComicDtos);

        return reversedComicDtos;
    }

    @Override
    @Transactional
    public void upVotes(String comicName) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double votes = comicsEntity.getVotes();
        votes++;
        comicsEntity.setVotes(votes);
        comicsRepository.save(comicsEntity);
    }

    @Override
    @Transactional
    public void downVotes(String comicName) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double votes = comicsEntity.getVotes();
        votes--;
        comicsEntity.setVotes(votes);
        comicsRepository.save(comicsEntity);
    }

    @Override
    public void updateRating(String comicName, double rate) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Comics name %s not found", comicName)));

        double rating = comicsEntity.getRating();
        rating += rate;

        double votes = comicsEntity.getVotes();
        rating /= votes;

        comicsEntity.setRating(rating);
        comicsRepository.save(comicsEntity);
    }
}

package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.repository.entities.ComicsEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ComicService {
    ComicsEntity saveComic(ComicDto comicDto);
    ComicsEntity getByName(String comicName);
    ComicsEntity getById(Integer id);
    List<ComicsEntity> getAll();
    ComicsEntity updateComic(ComicDto comicDto);
    String delete(String name);
    String deleteAll();
    List<ComicsEntity> findAll(String field, Boolean ascending, int page, int size);
    List<ComicsEntity> findMapAll(Map<String, Object> filters, Pageable pageable);
    void upVotes(String comicName);
    void downVotes(String comicName);
    void updateRating(String comicName, double rate);
}

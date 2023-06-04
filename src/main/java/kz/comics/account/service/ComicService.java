package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ComicService {
    ComicDto saveComic(ComicDto comicDto);
    ComicDto getComic(String comicName);
    List<ComicDto> getAll();
    ComicDto updateComic(ComicDto comicDto);
    ComicDto delete(String name);
    String deleteAll();
    List<ComicDto> findAll(String field, Boolean ascending, int page, int size);
    List<ComicDto> findMapAll(Map<String, Object> filters, Pageable pageable);
    void upVotes(String comicName);
    void downVotes(String comicName);
    void updateRating(String comicName, double rate);
}

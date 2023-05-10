package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;

import java.util.List;

public interface ComicService {
    ComicDto saveComic(ComicDto comicDto);
    ComicDto getComic(String comicName);
    List<ComicDto> getAll();
    ComicDto updateComic(ComicDto comicDto);
    ComicDto delete(String name);
    String deleteAll();
}

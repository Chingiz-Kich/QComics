package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.model.comics.ComicSaveDto;

import java.util.List;

public interface ComicService {
    ComicDto saveComic(ComicSaveDto comicSaveDto);
    ComicDto getComic(String comicName);
    List<ComicDto> getAll();
    ComicDto updateComic(ComicDto comicDto);
}

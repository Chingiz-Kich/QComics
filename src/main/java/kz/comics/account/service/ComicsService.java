package kz.comics.account.service;

import kz.comics.account.model.comics.ComicsDto;

public interface ComicsService {
    ComicsDto saveComics(ComicsDto comicsDto);
    ComicsDto getComics(String comicsName);
}

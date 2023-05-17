package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.repository.entities.UserEntity;

import java.util.List;

public interface BookmarkService {

    String addComicToBookmarks(String comicName, String username);
    String removeComicFromBookmark(String comicName, String username);
    List<ComicDto> getAllBookmarkedComics(String username);
    void deleteAllByUserEntity(UserEntity userEntity);
}

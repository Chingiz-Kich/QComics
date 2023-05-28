package kz.comics.account.service;

import kz.comics.account.model.comics.ComicDto;

import java.util.List;

public interface BookmarkService {

    String addComicToBookmarks(String comicName, String username);
    String removeComicFromBookmark(String comicName, String username);
    List<ComicDto> getAllBookmarkedComics(String username);
    Boolean isBookmarked(String username, String comicName);
}

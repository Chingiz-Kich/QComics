package kz.comics.account.service.impl;

import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.repository.BookmarkRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ComicEntity;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ComicsRepository comicsRepository;
    private final ComicServiceImpl comicServiceImpl;

    @Override
    public String addComicToBookmarks(String comicName, String username) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        List<ComicEntity> bookmarkEntities = userEntity.getBookmarks();
        if (bookmarkEntities.isEmpty()) {
            bookmarkEntities = new ArrayList<>();
        }
        bookmarkEntities.add(comicEntity);
        userEntity.setBookmarks(bookmarkEntities);
        userRepository.save(userEntity);
        return "Comic added to bookmark";
    }

    @Override
    public String removeComicFromBookmark(String comicName, String username) {
        ComicEntity comicEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        List<ComicEntity> comicsEntities = userEntity.getBookmarks();

        if (!comicsEntities.remove(comicEntity)) {
            return String.format("Comic with name %s does not exist in bookmark username: %s", comicName, username);
        }

        userRepository.save(userEntity);

        return "Comic deleted from bookmark";
    }

    @Override
    public List<ComicDto> getAllBookmarkedComics(String username) {
        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));


        return userEntity.getBookmarks()
                .stream()
                .map(comicServiceImpl::entityToDto)
                .toList();
    }

    @Override
    public void deleteAllByUserEntity(UserEntity userEntity) {
        bookmarkRepository.deleteAllByUser(userEntity);
    }
}

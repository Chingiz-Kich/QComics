package kz.comics.account.service.impl;

import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.LikeRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.LikeEntity;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserRepository userRepository;
    private final ComicsRepository comicsRepository;
    private final LikeRepository likeRepository;

    @Override
    public Boolean saveLike(Integer userId, Integer comicsId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        ComicsEntity comicsEntity = comicsRepository.findById(comicsId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comics with id: %s", comicsId)));

        LikeEntity likeEntity = LikeEntity
                .builder()
                .comics(comicsEntity)
                .user(userEntity)
                .build();

        likeRepository.save(likeEntity);
        return true;
    }

    @Override
    public Boolean hasLike(Integer userId, Integer comicsId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        ComicsEntity comicsEntity = comicsRepository.findById(comicsId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comics with id: %s", comicsId)));

        Optional<LikeEntity> likeEntity = likeRepository.findByUserAndComics(userEntity, comicsEntity);

        return likeEntity.isPresent();
    }
}

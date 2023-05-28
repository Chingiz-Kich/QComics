package kz.comics.account.service.impl;

import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.LikeRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ChapterEntity;
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
    private final ChapterRepository chapterRepository;
    private final LikeRepository likeRepository;

    @Override
    public Boolean saveLike(Integer userId, Integer chapterId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterId)));

        LikeEntity likeEntity = LikeEntity
                .builder()
                .chapter(chapterEntity)
                .user(userEntity)
                .build();

        likeRepository.save(likeEntity);
        return true;
    }

    @Override
    public Boolean hasLike(Integer userId, Integer chapterId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterId)));

        Optional<LikeEntity> likeEntity = likeRepository.findByUserAndChapter(userEntity, chapterEntity);

        return likeEntity.isPresent();
    }
}

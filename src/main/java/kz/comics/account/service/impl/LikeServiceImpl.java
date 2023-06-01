package kz.comics.account.service.impl;

import kz.comics.account.repository.*;
import kz.comics.account.repository.entities.*;
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
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

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

    @Override
    public Boolean saveCommentLike(Integer userId, Integer commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("ni"));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("poxui"));

        commentLikeRepository.save(CommentLikeEntity.builder().commentEntity(commentEntity).userEntity(userEntity).build());
        return true;
    }

    @Override
    public Boolean hasCommentLike(Integer userId, Integer commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("ni"));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("poxui"));

        return commentLikeRepository.findByCommentEntityAndUserEntity(commentEntity, userEntity).isPresent();
    }
}

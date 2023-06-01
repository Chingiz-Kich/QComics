package kz.comics.account.service.impl;

import kz.comics.account.model.comment.CommentSaveRequest;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.CommentRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.CommentEntity;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;

    @Override
    public String save(CommentSaveRequest commentSaveRequest) {
        ChapterEntity chapterEntity = chapterRepository.findById(commentSaveRequest.getChapterId())
                .orElseThrow(() -> new NoSuchElementException("Cannot find chapterId from request"));

        UserEntity userEntity = userRepository.findById(commentSaveRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Cannot find user by id"));

        CommentEntity commentEntity = CommentEntity
                .builder()
                .chapterEntity(chapterEntity)
                .userEntity(userEntity)
                .content(commentSaveRequest.getContent())
                .likeType(commentSaveRequest.getCommentLikeType())
                .createdDate(commentSaveRequest.getCreatedDate())
                .build();

        commentRepository.save(commentEntity);
        return "Saved";

    }

    @Override
    public List<CommentEntity> getCommentsByChapterId(Integer chapterId) {
        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find chapter with id"));

        return commentRepository.findAllByChapterEntity(chapterEntity);
    }
}

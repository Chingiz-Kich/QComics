package kz.comics.account.service;

import kz.comics.account.model.comment.CommentSaveRequest;
import kz.comics.account.repository.entities.CommentEntity;

import java.util.List;


public interface CommentService {
    String save(CommentSaveRequest commentSaveRequest);
    List<CommentEntity> getCommentsByChapterId(Integer chapterId);
}

package kz.comics.account.service;

public interface LikeService {
    Boolean saveLike(Integer userId, Integer comicsId);
    Boolean hasLike(Integer userId, Integer comicsId);
    Boolean saveCommentLike(Integer userId, Integer commentId);
    Boolean hasCommentLike(Integer userId, Integer commentId);
}

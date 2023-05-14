package kz.comics.account.service;

import kz.comics.account.repository.entities.LikeEntity;

public interface LikeService {
    Boolean saveLike(Integer userId, Integer comicsId);
    Boolean hasLike(Integer userId, Integer comicsId);
}

package kz.comics.account.service;

import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserDto getById(Integer id);
    UserDto getByUsername(String username);
    String deleteByUsername(String username);
    UserDto update(UserUpdateRequest userUpdateRequest);
    String deleteById(Integer id);
    String deleteAll();
    String subscribe(Integer subscriberId, Integer userToSubscribeId);
    List<UserDto> getUserSubscriptions(Integer userId);
    List<UserDto> getUserSubscribers(Integer userId);
    String unsubscribe(Integer subscriberId, Integer userToUnsubscribeId);
    String subscribe(String subscriberUsername, String userToSubscribeUsername);
    List<UserDto> getUserSubscriptions(String username);
    List<UserDto> getUserSubscribers(String username);
    String unsubscribe(String subscriberUsername, String userToUnsubscribeUsername);
    int getSubscribersAmount(Integer userId);
    int getSubscribersAmount(String username);
}

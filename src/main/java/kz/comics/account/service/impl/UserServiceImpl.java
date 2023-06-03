package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.UserMapper;
import kz.comics.account.model.user.UserDto;
//import kz.comics.account.repository.UserSubscriptionRepository;
import kz.comics.account.model.user.UserUpdateById;
import kz.comics.account.model.user.UserUpdateByUsername;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.model.user.UserUpdateRequest;
import kz.comics.account.repository.UserRepository;
//import kz.comics.account.repository.entities.UserSubscriptionEntity;
import kz.comics.account.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    //private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    @SneakyThrows
    public UserDto getById(Integer id) {
        log.info("In UserService. getById: {}", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Id: %s not found", id)));

        log.info("In UserService. Got user from repository: {}", objectMapper.writeValueAsString(userEntity));
        return userMapper.toDto(userEntity);
    }

    @SneakyThrows
    @Override
    public UserDto updateById(UserUpdateById userUpdateById) {
        log.info("In UserService. updatedUser: {}", objectMapper.writeValueAsString(userUpdateById));

        UserEntity userEntity = userRepository.findById(userUpdateById.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userUpdateById.getId())));

        UserEntity updatedEntity = this.getUpdatedUserEntity(userEntity, userUpdateById);
        UserEntity savedEntity = userRepository.save(updatedEntity);
        log.info("In updateById. Update user from repository: {}", objectMapper.writeValueAsString(savedEntity));
        return userMapper.toDto(userEntity);
    }

    @SneakyThrows
    @Override
    public UserDto updateByUsername(UserUpdateByUsername userUpdateByUsername) {
        log.info("In UserService. updatedUser: {}", objectMapper.writeValueAsString(userUpdateByUsername));

        UserEntity userEntity = userRepository.findUserByUsername(userUpdateByUsername.getUsername())
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", userUpdateByUsername.getUsername())));

        UserEntity updatedEntity = this.getUpdatedUserEntity(userEntity, userUpdateByUsername);
        UserEntity savedEntity = userRepository.save(updatedEntity);
        log.info("In updateByUsername. Update user from repository: {}", objectMapper.writeValueAsString(savedEntity));
        return userMapper.toDto(savedEntity);
    }

    @Override
    @SneakyThrows
    public String deleteById(Integer id)  {
        log.info("In UserService. deleteById: {}", id);

        userRepository.deleteById(id);

        return "User deleted";
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "easy peasy lemon squeezy";
    }

    @Override
    public String subscribe(Integer subscriberId, Integer userToSubscribeId) {
        UserEntity subscriber = userRepository.findById(subscriberId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", subscriberId)));

        UserEntity userToSubscribe = userRepository.findById(userToSubscribeId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userToSubscribeId)));

/*        UserSubscriptionEntity userSubscriptionEntity = UserSubscriptionEntity
                .builder()
                .subscriber(subscriber)
                .userToSubscribe(userToSubscribe)
                .build();*/

        subscriber.getSubscriptions().add(userToSubscribe);

        // userSubscriptionRepository.save(userSubscriptionEntity);
        userRepository.save(subscriber);
        return "Subscribed";
    }

    @Override
    public List<UserDto> getUserSubscriptions(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));
        //List<UserEntity> subscriptions = userSubscriptionRepository.findByUserToSubscribe(user);
        List<UserEntity> subscriptions =  user.getSubscriptions();
        return subscriptions.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getUserSubscribers(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        List<UserEntity> subscribers = user.getSubscribers();
        return subscribers.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public String unsubscribe(Integer subscriberId, Integer userToUnsubscribeId) {
        UserEntity subscriber = userRepository.findById(subscriberId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", subscriberId)));

        UserEntity userToSubscribe = userRepository.findById(userToUnsubscribeId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userToUnsubscribeId)));

        subscriber.getSubscriptions().remove(userToSubscribe);
        userRepository.save(subscriber);
        return "Unsubscribed";
    }

    @Override
    public String subscribe(String subscriberUsername, String userToSubscribeUsername) {
        UserEntity subscriber = userRepository.findUserByUsername(subscriberUsername)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", subscriberUsername)));

        UserEntity userToSubscribe = userRepository.findUserByUsername(userToSubscribeUsername)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", userToSubscribeUsername)));

/*        UserSubscriptionEntity userSubscriptionEntity = UserSubscriptionEntity
                .builder()
                .subscriber(subscriber)
                .userToSubscribe(userToSubscribe)
                .build();*/
        subscriber.getSubscriptions().add(userToSubscribe);
        userRepository.save(subscriber);
        //userSubscriptionRepository.save(userSubscriptionEntity);
        return "Subscribed";
    }

    @Override
    public List<UserDto> getUserSubscriptions(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
        //UserEntity user = userSubscriptionRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));
        List<UserEntity> subscriptions =  user.getSubscriptions();
        return subscriptions.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getUserSubscribers(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        List<UserEntity> subscribers = user.getSubscribers();
        return subscribers.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public String unsubscribe(String subscriberUsername, String userToUnsubscribeUsername) {
        UserEntity subscriber = userRepository.findUserByUsername(subscriberUsername)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", subscriberUsername)));

        UserEntity userToSubscribe = userRepository.findUserByUsername(userToUnsubscribeUsername)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with usernmae: %s", userToUnsubscribeUsername)));

        subscriber.getSubscriptions().remove(userToSubscribe);
        userRepository.save(subscriber);
        return "Unsubscribed";
    }

    @SneakyThrows
    @Override
    public UserDto getByUsername(String username) {
        log.info("In UserService. getByUsername: {}", username);

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Username %s not found", username)));

        log.info("In UserService. Got user from repository: {}", objectMapper.writeValueAsString(userEntity));
        return userMapper.toDto(userEntity);
    }

    @SneakyThrows
    @Override
    public String deleteByUsername(String username)  {
        log.info("In UserService. deleteByUsername: {}", username);

        userRepository.deleteByUsername(username);

        log.info("In UserService. Deleted user from repository: {}", username);
        return "easy peasy lemon squeezy";
    }

    @Override
    public int getSubscribersAmount(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));

        List<UserEntity> subscribers = user.getSubscribers();
        return subscribers.size();
    }

    @Override
    public int getSubscribersAmount(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        List<UserEntity> subscribers = user.getSubscribers();
        return subscribers.size();
    }

    private UserEntity getUpdatedUserEntity(UserEntity userEntity, UserUpdateRequest updateRequest) {

        if (StringUtils.isNotBlank(updateRequest.getPassword())) {
            userEntity.setPassword(updateRequest.getPassword());
        }

        if (StringUtils.isNotBlank(updateRequest.getEmail())) {
            userEntity.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getRole() != null) {
            userEntity.setRole(updateRequest.getRole());
        }

        if (StringUtils.isNotBlank(updateRequest.getAvatarBase64())) {
            userEntity.setAvatar(Base64.getDecoder().decode(updateRequest.getAvatarBase64()));
        }

        return userEntity;
    }
}

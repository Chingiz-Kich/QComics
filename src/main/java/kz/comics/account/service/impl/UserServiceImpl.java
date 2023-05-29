package kz.comics.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.UserMapper;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.model.user.UserUpdateRequest;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @SneakyThrows
    public UserDto getById(Integer id) {
        log.info("In UserService. getById: {}", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Id: %s not found", id)));

        log.info("In UserService. Got user from repository: {}", objectMapper.writeValueAsString(userEntity));
        return userMapper.toDto(userEntity);
    }

    @Override
    @SneakyThrows
    public String deleteById(Integer id)  {
        log.info("In UserService. deleteById: {}", id);

        userRepository.deleteById(id);

        return "User deleted";
    }

    @Override
    @SneakyThrows
    public UserDto update(UserUpdateRequest updatedUser) {
        log.info("In UserService. updatedUser: {}", objectMapper.writeValueAsString(updatedUser));

        Optional<UserEntity> optionalUser = userRepository.findUserByUsername(Optional.ofNullable(updatedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("In user update null found")));

        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }


        UserEntity userEntity = UserEntity.builder()
                .username(updatedUser.getUsername())
                .build();

        if (StringUtils.isNotBlank(updatedUser.getPassword())) {
            userEntity.setPassword(updatedUser.getPassword());
        }

        if (StringUtils.isNotBlank(updatedUser.getEmail())) {
            userEntity.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getRole() != null) {
            userEntity.setRole(updatedUser.getRole());
        }

        if (StringUtils.isNotBlank(updatedUser.getAvatarBase64())) {
            userEntity.setAvatar(Base64.getDecoder().decode(updatedUser.getAvatarBase64()));
        }

        UserEntity userEntitySaved = userRepository.save(userEntity);
        log.info("In UserService. Update user from repository: {}", objectMapper.writeValueAsString(userEntitySaved));
        return userMapper.toDto(userEntitySaved);
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

        subscriber.getSubscriptions().add(userToSubscribe);
        userRepository.save(subscriber);
        return "Subscribed";
    }

    @Override
    public List<UserDto> getUserSubscriptions(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with id: %s", userId)));
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

        subscriber.getSubscriptions().add(userToSubscribe);
        userRepository.save(subscriber);
        return "Subscribed";
    }

    @Override
    public List<UserDto> getUserSubscriptions(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
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
}

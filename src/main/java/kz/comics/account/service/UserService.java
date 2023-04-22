package kz.comics.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.mapper.UserMapper;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserEntity;
import kz.comics.account.model.user.UserUpdateRequest;
import kz.comics.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    public UserDto getByUsername(String username) throws JsonProcessingException {
        log.info("In UserService. getByUsername: {}", username);

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        log.info("In UserService. Got user from repository: {}", objectMapper.writeValueAsString(userEntity));
        return userMapper.toDto(userEntity);
    }

    public UserDto deleteByUsername(String username) throws JsonProcessingException {
        log.info("In UserService. deleteByUsername: {}", username);

        UserEntity userEntity = userRepository.deleteByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        log.info("In UserService. Deleted user from repository: {}", objectMapper.writeValueAsString(userEntity));
        return userMapper.toDto(userEntity);
    }

    public UserDto update(UserUpdateRequest updatedUser) throws JsonProcessingException {
        log.info("In UserService. updatedUser: {}", objectMapper.writeValueAsString(updatedUser));

        Optional<UserEntity> optionalUser = userRepository.findUserByUsername(Optional.ofNullable(updatedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("In user update null found")));

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
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

        UserEntity userEntitySaved = userRepository.save(userEntity);
        log.info("In UserService. Update user from repository: {}", objectMapper.writeValueAsString(userEntitySaved));
        return userMapper.toDto(userEntitySaved);
    }
}

package kz.comics.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.model.User;
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

    public User getByUsername(String username) throws JsonProcessingException {
        log.info("In UserService. getByUsername: {}", username);

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        log.info("In UserService. Got user from repository: {}", objectMapper.writeValueAsString(user));
        return user;
    }

    public User deleteByUsername(String username) throws JsonProcessingException {
        log.info("In UserService. deleteByUsername: {}", username);

        User user = userRepository.deleteByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        log.info("In UserService. Deleted user from repository: {}", objectMapper.writeValueAsString(user));
        return user;
    }

    public User update(UserUpdateRequest updatedUser) throws JsonProcessingException {
        log.info("In UserService. updatedUser: {}", objectMapper.writeValueAsString(updatedUser));

        Optional<User> optionalUser = userRepository.findUserByUsername(Optional.ofNullable(updatedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("In user update null found")));

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }


        User user = User.builder()
                .username(updatedUser.getUsername())
                .build();

        if (StringUtils.isNotBlank(updatedUser.getPassword())) {
            user.setPassword(updatedUser.getPassword());
        }

        if (StringUtils.isNotBlank(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        log.info("In UserService. Update user from repository: {}", objectMapper.writeValueAsString(user));
        return userRepository.save(user);
    }
}

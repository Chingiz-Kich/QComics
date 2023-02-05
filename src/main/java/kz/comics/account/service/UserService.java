package kz.comics.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.comics.account.model.User;
import kz.comics.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}

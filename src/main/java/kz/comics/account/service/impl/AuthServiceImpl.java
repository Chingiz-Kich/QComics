package kz.comics.account.service.impl;

import kz.comics.account.model.user.Role;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.model.auth.AuthenticationRequest;
import kz.comics.account.model.auth.AuthenticationResponse;
import kz.comics.account.model.auth.RegistrationRequest;
import kz.comics.account.model.mail.MailDto;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.service.AuthService;
import kz.comics.account.service.JwtService;
import kz.comics.account.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final Map<String, UserEntity> userCache = new HashMap<>();
    private final Map<String, Integer> codeCache = new HashMap<>();
    private static final Random random = new Random();

    @Override
    public String register(RegistrationRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new IllegalStateException(String.format("User with username: %s already exist!", request.getUsername()));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format("User with email: %s already exist", request.getEmail()));
        }

        int checkNumber = random.nextInt(9999 - 1000 + 1) + 1000;

        MailDto mailDto = new MailDto();
        mailDto.setRecipient(request.getEmail());
        mailDto.setSubject("Проверочный код");
        mailDto.setMsgBody("КОД: " + checkNumber);

        codeCache.put(request.getUsername(), checkNumber);
        userCache.put(request.getUsername(), userEntity);

        CompletableFuture<Void> emailFuture = CompletableFuture.runAsync(() -> mailService.sendAuth(mailDto));

        // Wait for the email to be sent before returning the UserDto
        emailFuture.join();
        userRepository.save(userEntity);
        return "Email sent";
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()
                )
        );

        // Если эта строчка выполняется, значит user authenticated
        UserEntity userEntity = userRepository.findUserByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s not found", authenticationRequest.getUsername())));

        String jwt = jwtService.generateToken(userEntity);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public String validateNumber(String username, Integer number) {

        if (Objects.equals(codeCache.get(username), number)) {
            codeCache.remove(username);
            userCache.remove(username);
            return "User saved successfully";
        } else {
            codeCache.remove(username);
            userCache.remove(username);
            return "Failed";
        }
    }

    @Override
    public String getToken() {
        return jwtService.generateToken(UserEntity
                        .builder()
                        .id(123)
                        .username("bla-bla")
                        .role(Role.USER)
                        .password("pass")
                        .email("e@mail.ru")
                        .build());
    }
}

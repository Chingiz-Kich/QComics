package kz.comics.account.service;

import kz.comics.account.mapper.UserMapper;
import kz.comics.account.model.user.Role;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.model.auth.AuthenticationRequest;
import kz.comics.account.model.auth.AuthenticationResponse;
import kz.comics.account.model.auth.RegistrationRequest;
import kz.comics.account.model.mail.MailDto;
import kz.comics.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final UserMapper userMapper;

    public UserDto register(RegistrationRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();


        MailDto mailDto = new MailDto();
        mailDto.setRecipient(request.getEmail());
        mailDto.setSubject("Тестовая регистрация");
        mailDto.setMsgBody("Поздравляю с регистрацией :) \n Данная регистрационная форма сделана исключительно для теста");
        mailService.sendMail(mailDto);
        return userMapper.toDto(userRepository.save(userEntity));
    }

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
}

package kz.comics.account.service;

import kz.comics.account.model.Role;
import kz.comics.account.model.User;
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

    public User register(RegistrationRequest request) {
        User user = User.builder()
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
        return userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()
                )
        );

        // Если эта строчка выполняется, значит user authenticated
        User user = userRepository.findUserByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s not found", authenticationRequest.getUsername())));

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}

package kz.comics.account.service;

import kz.comics.account.model.auth.AuthenticationRequest;
import kz.comics.account.model.auth.AuthenticationResponse;
import kz.comics.account.model.auth.RegistrationRequest;

public interface AuthService {
    String register(RegistrationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    String validateNumber(String username, Integer number);
    String getToken();
}

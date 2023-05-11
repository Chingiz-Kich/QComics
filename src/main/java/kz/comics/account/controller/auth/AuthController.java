package kz.comics.account.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserValidateNumber;
import kz.comics.account.service.AuthService;
import kz.comics.account.model.auth.AuthenticationRequest;
import kz.comics.account.model.auth.AuthenticationResponse;
import kz.comics.account.model.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login user")
    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @Operation(summary = "Validate the number sent to email")
    @PostMapping(path = "/validate")
    public ResponseEntity<String> validate(@RequestBody UserValidateNumber userValidateNumber) {
        return ResponseEntity.ok(authService.validateNumber(userValidateNumber.getUsername(), userValidateNumber.getNumber()));
    }

}

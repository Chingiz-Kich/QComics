package kz.comics.account.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.service.UserService;
import kz.comics.account.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by username")
    @GetMapping("/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) throws JsonProcessingException {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @Operation(summary = "Delete user account by username")
    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteByUsername(@PathVariable String username) throws JsonProcessingException {
        return ResponseEntity.ok(userService.deleteByUsername(username));
    }
}

package kz.comics.account.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserUpdateRequest;
import kz.comics.account.service.UserService;
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
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) throws JsonProcessingException {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @Operation(summary = "Delete user account by username")
    @DeleteMapping("/{username}")
    public ResponseEntity<UserDto> deleteByUsername(@PathVariable String username) throws JsonProcessingException {
        return ResponseEntity.ok(userService.deleteByUsername(username));
    }

    @Operation(summary = "Update user (We have to separate upd for password change and other stuff. This is just quick fix)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "In user update null found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IllegalArgumentException.class))),
    })
    @PostMapping(path = "/update")
    public ResponseEntity<UserDto> update(@RequestBody UserUpdateRequest updatedUser) throws JsonProcessingException {
        return ResponseEntity.ok(userService.update(updatedUser));
    }

    @Operation(summary = "Destroy users")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(userService.deleteAll());
    }
}

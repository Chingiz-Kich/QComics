package kz.comics.account.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.comics.account.model.user.UserDto;
import kz.comics.account.model.user.UserUpdateById;
import kz.comics.account.model.user.UserUpdateByUsername;
import kz.comics.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by id")
    @GetMapping("/ids/{id}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Get user by username")
    @GetMapping("/usernames/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @Operation(summary = "Delete user account by шв")
    @DeleteMapping("/ids/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @Operation(summary = "Delete user account by username")
    @DeleteMapping("/usernames/{username}")
    public ResponseEntity<String> deleteByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.deleteByUsername(username));
    }

    @Operation(summary = "Update user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "In user update null found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IllegalArgumentException.class))),

            @ApiResponse(responseCode = "500", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsernameNotFoundException.class)))
    })
    @PostMapping(path = "/update-by-username")
    public ResponseEntity<UserDto> updateByUsername(@RequestBody UserUpdateByUsername updatedUser) {
        return ResponseEntity.ok(userService.updateByUsername(updatedUser));
    }

    @PostMapping(path = "/update-by-id")
    public ResponseEntity<UserDto> updateById(@RequestBody UserUpdateById updatedUser) {
        return ResponseEntity.ok(userService.updateById(updatedUser));
    }

    @Operation(summary = "Destroy users")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(userService.deleteAll());
    }

    @Operation(summary = "Subscribe by user id")
    @PostMapping("/subscribe/id")
    public ResponseEntity<String> subscribeById(@RequestParam Integer subscriberId, @RequestParam Integer userToSubscribeId) {
        return ResponseEntity.ok(userService.subscribe(subscriberId, userToSubscribeId));
    }

    @Operation(summary = "Subscribe by user username")
    @PostMapping("/subscribe/username")
    public ResponseEntity<String> subscribeByUsername(@RequestParam String subscriberUsername, @RequestParam String userToSubscribeUsername) {
        return ResponseEntity.ok(userService.subscribe(subscriberUsername, userToSubscribeUsername));
    }

    @Operation(summary = "Get user subscriptions by id")
    @GetMapping("/subscriptions/ids/{userId}")
    public ResponseEntity<List<UserDto>> getUserSubscriptionsById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserSubscriptions(userId));
    }

    @Operation(summary = "Get user subscriptions by username")
    @GetMapping("/subscriptions/usernames/{username}")
    public ResponseEntity<List<UserDto>> getUserSubscriptionsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserSubscriptions(username));
    }

    @Operation(summary = "Get user subscribers by id")
    @GetMapping("/subscribers/ids/{userId}")
    public ResponseEntity<List<UserDto>> getUserSubscribersById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserSubscribers(userId));
    }

    @Operation(summary = "Get user subscribers by username")
    @GetMapping("/subscribers/usernames/{username}")
    public ResponseEntity<List<UserDto>> getUserSubscribersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserSubscribers(username));
    }

    @Operation(summary = "Unsubscribe by id")
    @PostMapping("/unsubscribe/id")
    public ResponseEntity<String> unsubscribeById(@RequestParam Integer subscriberId, @RequestParam Integer userToUnsubscribeId) {
        return ResponseEntity.ok(userService.unsubscribe(subscriberId, userToUnsubscribeId));
    }

    @Operation(summary = "Unsubscribe by username")
    @PostMapping("/unsubscribe/username")
    public ResponseEntity<String> unsubscribeByUsername(@RequestParam String subscriberUsername, @RequestParam String userToUnsubscribeUsername) {
        return ResponseEntity.ok(userService.unsubscribe(subscriberUsername, userToUnsubscribeUsername));
    }

    @Operation(summary = "Get subscribers number by id")
    @GetMapping("/subscribers/amount/ids/{id}")
    public ResponseEntity<Integer> getSubscribersAmountById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getSubscribersAmount(id));
    }

    @Operation(summary = "Get subscribers number by username")
    @GetMapping("/subscribers/amount/usernames/{username}")
    public ResponseEntity<Integer> getSubscribersAmountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getSubscribersAmount(username));
    }
}

package kz.comics.account.controller.like;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.like.LikeSaveDto;
import kz.comics.account.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "Save like")
    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody LikeSaveDto likeSaveDto) {
        return ResponseEntity.ok(likeService.saveLike(likeSaveDto.getUserId(), likeSaveDto.getComicId()));
    }

    @Operation(summary = "Check if specific comic has like from particular user")
    @GetMapping("/{userId}/{comicId}")
    public ResponseEntity<Boolean> hasLike(@PathVariable Integer userId, @PathVariable Integer comicId) {
        return ResponseEntity.ok(likeService.hasLike(userId, comicId));
    }
}

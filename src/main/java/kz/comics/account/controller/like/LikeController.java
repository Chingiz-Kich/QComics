package kz.comics.account.controller.like;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.like.CommentLikeSave;
import kz.comics.account.model.like.LikeSaveDto;
import kz.comics.account.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "Save like")
    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody LikeSaveDto likeSaveDto) {
        return ResponseEntity.ok(likeService.saveLike(likeSaveDto.getUserId(), likeSaveDto.getChapterId()));
    }

    @Operation(summary = "Check if specific comic has like from particular user")
    @GetMapping("/{userId}/{chapterId}")
    public ResponseEntity<Boolean> hasLike(@PathVariable Integer userId, @PathVariable Integer chapterId) {
        return ResponseEntity.ok(likeService.hasLike(userId, chapterId));
    }

    @PostMapping("/comment/save")
    public ResponseEntity<Boolean> saveCommentLike(@RequestBody CommentLikeSave commentLikeSave) {
        return ResponseEntity.ok(likeService.saveCommentLike(commentLikeSave.getUserId(), commentLikeSave.getCommentId()));
    }

    @GetMapping("/comment/{commentId}/{userId}")
    public ResponseEntity<Boolean> hasCommentLike(@PathVariable Integer commentId, @PathVariable Integer userId) {
        return ResponseEntity.ok(likeService.hasCommentLike(userId, commentId));
    }
}

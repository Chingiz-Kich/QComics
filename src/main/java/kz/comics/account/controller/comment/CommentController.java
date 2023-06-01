package kz.comics.account.controller.comment;

import kz.comics.account.model.comment.CommentSaveRequest;
import kz.comics.account.repository.entities.CommentEntity;
import kz.comics.account.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/save")
    public ResponseEntity<String> save(@RequestBody CommentSaveRequest commentSaveRequest) {
        return ResponseEntity.ok(commentService.save(commentSaveRequest));
    }

    @GetMapping(path = "/chapterId")
    public ResponseEntity<List<CommentEntity>> getAllByChapterId(@PathVariable Integer chapterId) {
        return ResponseEntity.ok(commentService.getCommentsByChapterId(chapterId));
    }
}

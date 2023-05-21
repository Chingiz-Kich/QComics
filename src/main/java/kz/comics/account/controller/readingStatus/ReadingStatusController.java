package kz.comics.account.controller.readingStatus;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.chapter.ChapterReadStatus;
import kz.comics.account.model.readingStatus.ReadingStatusRequest;
import kz.comics.account.service.ReadingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/reading-status")
@RequiredArgsConstructor
public class ReadingStatusController {

    private final ReadingStatusService readingStatusService;

    @Operation(summary = "Mark chapter as already read")
    @PostMapping(path = "/read")
    public ResponseEntity<String> markChapterAsRead(@RequestBody ReadingStatusRequest readingStatusRequest) {
        return ResponseEntity.ok(readingStatusService.markChapterAsRead(readingStatusRequest.getUsername(), readingStatusRequest.getChapterId()));
    }

    @Operation(summary = "Mark chapter as unread")
    @PostMapping(path = "/unread")
    public ResponseEntity<String> markChapterAsUnread(@RequestBody ReadingStatusRequest readingStatusRequest) {
        return ResponseEntity.ok(readingStatusService.markChapterAsUnread(readingStatusRequest.getUsername(), readingStatusRequest.getChapterId()));
    }

    @Operation(summary = "Get status")
    @GetMapping(path = "")
    public ResponseEntity<ChapterReadStatus> isChapterReadByUser(@RequestParam String username, @RequestParam Integer chapterId) {
        return ResponseEntity.ok(readingStatusService.isChapterReadByUser(username, chapterId));
    }
}

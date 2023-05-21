package kz.comics.account.controller.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.bookmark.BookmarkDto;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "Add comic to bookmark")
    @PostMapping("/add")
    public ResponseEntity<String> addComicToBookmark(@RequestBody BookmarkDto bookmarkDto) {
        return ResponseEntity.ok(bookmarkService.addComicToBookmarks(bookmarkDto.getComicName(), bookmarkDto.getUsername()));
    }

    @Operation(summary = "Remove comic from bookmark")
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeComicFromBookmark(@RequestBody BookmarkDto bookmarkDto) {
        return ResponseEntity.ok(bookmarkService.removeComicFromBookmark(bookmarkDto.getComicName(), bookmarkDto.getUsername()));
    }

    @Operation(summary = "Get all bookmarked comics")
    @GetMapping("/all")
    public ResponseEntity<List<ComicDto>> getAllBookmarkedComics(@RequestParam String username) {
        return ResponseEntity.ok(bookmarkService.getAllBookmarkedComics(username));
    }

    @Operation(summary = "Check if certain comic is bookmarked")
    @GetMapping("/check")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam String username, @RequestParam String comicName) {
        return ResponseEntity.ok(bookmarkService.isBookmarked(username, comicName));
    }
}

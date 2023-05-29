package kz.comics.account.controller.chapter;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.chapter.ChapterDto;
import kz.comics.account.model.chapter.ChapterSaveDto;
import kz.comics.account.model.chapter.ChapterUpdate;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "Saving chapter by comic name")
    @PostMapping("/save-by-name")
    public ResponseEntity<ChapterDto> saveByComicName(@RequestBody ChapterSaveDto chapterSaveDto) {
        return ResponseEntity.ok(chapterService.saveByComicName(chapterSaveDto));
    }

    @Operation(summary = "Saving chapter by comic id")
    @PostMapping("/save-by-id")
    public ResponseEntity<ChapterDto> saveByComicId(@RequestBody ChapterSaveDto chapterSaveDto) {
        return ResponseEntity.ok(chapterService.saveByComicId(chapterSaveDto));
    }

    @Operation(summary = "Get all chapters")
    @GetMapping("/all")
    public ResponseEntity<List<ChapterDto>> getAll() {
        return ResponseEntity.ok(chapterService.getAll());
    }

    @Operation(summary = "Get chapters by comicsname")
    @GetMapping("/comic-names/{comicName}")
    public ResponseEntity<List<ChapterDto>> getByComicName(@PathVariable String comicName) {
        return ResponseEntity.ok(chapterService.getByComicName(comicName));
    }

    @Operation(summary = "Get chapters by comic id")
    @GetMapping("/comic-ids/{comicId}")
    public ResponseEntity<List<ChapterDto>> getByComicId(@PathVariable Integer comicId) {
        return ResponseEntity.ok(chapterService.getByComicId(comicId));
    }

    @Operation(summary = "Delete chapter by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(chapterService.deleteById(id));
    }

    @Operation(summary = "Update chapter. U can update only chapter name :)")
    @PutMapping("")
    public ResponseEntity<ChapterDto> update(@RequestBody ChapterUpdate chapterUpdate) {
        return ResponseEntity.ok(chapterService.update(chapterUpdate));
    }

    @Operation(summary = "Delete all chapters")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(chapterService.deleteAll());
    }
}

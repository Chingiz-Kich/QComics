package kz.comics.account.controller.chapter;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.model.comics.ChapterUpdate;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "Saving chapter")
    @PostMapping("/save")
    public ResponseEntity<ChapterDto> save(@RequestBody ChapterSaveDto chapterSaveDto) {
        return ResponseEntity.ok(chapterService.save(chapterSaveDto));
    }

    @Operation(summary = "Get all chapters")
    @GetMapping("/all")
    public ResponseEntity<List<ChapterDto>> getAll() {
        return ResponseEntity.ok(chapterService.getAll());
    }

    @Operation(summary = "Get chapters by comics name")
    @GetMapping("/{comicName}")
    public ResponseEntity<List<ChapterDto>> getByComicName(@PathVariable String comicName) {
        return ResponseEntity.ok(chapterService.getByComicName(comicName));
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

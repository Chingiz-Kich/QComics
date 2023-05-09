package kz.comics.account.controller.chapter;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

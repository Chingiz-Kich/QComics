package kz.comics.account.controller.chapter;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "Saving chapter")
    @PostMapping("/save")
    public ResponseEntity<ChapterDto> save(@RequestBody ChapterDto chapterDto) {
        return ResponseEntity.ok(chapterService.save(chapterDto));
    }
}

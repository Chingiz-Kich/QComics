package kz.comics.account.controller.comics;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comic")
@RequiredArgsConstructor
public class ComicController {

    private final ComicService comicService;

    @Operation(summary = "Save new comic")
    @PostMapping(path = "/save")
    public ResponseEntity<ComicDto> save(@RequestBody ComicDto comicDto) {
        return ResponseEntity.ok(comicService.saveComic(comicDto));
    }

    @Operation(summary = "Get comic by name")
    @GetMapping("/{name}")
    public ResponseEntity<ComicDto> getByName(@PathVariable String name) {
        return ResponseEntity.ok(comicService.getComic(name));
    }

    @Operation(summary = "Get all comics")
    @GetMapping("/all")
    public ResponseEntity<List<ComicDto>> getAll() {
        return ResponseEntity.ok(comicService.getAll());
    }

    @Operation(summary = "Update comic")
    @PutMapping("/update")
    public ResponseEntity<ComicDto> update(@RequestBody ComicDto comicDto) {
        return ResponseEntity.ok(comicService.updateComic(comicDto));
    }
}

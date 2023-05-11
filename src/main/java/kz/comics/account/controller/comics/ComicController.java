package kz.comics.account.controller.comics;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.service.ComicService;
import kz.comics.account.util.FilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chingiz
 */
@CrossOrigin
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

    @Operation(summary = "Save all comics")
    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody List<ComicDto> comicDtos) {
        comicDtos.forEach(comicService::saveComic);
        return ResponseEntity.ok("All comics saved");
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

    @Operation(summary = "Find by specific field")
    @PostMapping("/findAll/filter")
    public ResponseEntity<List<ComicDto>> findAll(@RequestBody FilterRequest filterRequest) {
        return ResponseEntity.ok(comicService.findAll(filterRequest.getField(), filterRequest.getAscending(), filterRequest.getPage(), filterRequest.getSize()));
    }

    @Operation(summary = "Update comic")
    @PutMapping("/update")
    public ResponseEntity<ComicDto> update(@RequestBody ComicDto comicDto) {
        return ResponseEntity.ok(comicService.updateComic(comicDto));
    }

    @Operation(summary = "Delete comic")
    @DeleteMapping("/{name}")
    public ResponseEntity<ComicDto> delete(@PathVariable String name) {
        return ResponseEntity.ok(comicService.delete(name));
    }

    @Operation(summary = "Delete all comics")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(comicService.deleteAll());
    }
}

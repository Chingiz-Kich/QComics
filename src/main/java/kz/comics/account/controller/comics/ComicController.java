package kz.comics.account.controller.comics;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.mapper.ComicsMapper;
import kz.comics.account.model.comics.ComicDto;
import kz.comics.account.service.ComicService;
import kz.comics.account.util.FilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Chingiz
 */
@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/comics")
@RequiredArgsConstructor
public class ComicController {

    private final ComicService comicService;
    private final ComicsMapper comicsMapper;

    @Operation(summary = "Save new comic")
    @PostMapping(path = "/save")
    public ResponseEntity<ComicDto> save(@RequestBody ComicDto comicDto) {
        return ResponseEntity.ok(comicsMapper.entityToDto(comicService.saveComic(comicDto)));
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
        return ResponseEntity.ok(comicsMapper.entityToDto(comicService.getByName(name)));
    }

    @Operation(summary = "Get all comics")
    @GetMapping("/all")
    public ResponseEntity<List<ComicDto>> getAll() {
        return ResponseEntity.ok(comicService.getAll()
                .stream()
                .map(comicsMapper::entityToDto)
                .toList());
    }

    @Operation(summary = "Find by specific field")
    @PostMapping("/findAll/filter")
    public ResponseEntity<List<ComicDto>> findAll(@RequestBody FilterRequest filterRequest) {
        return ResponseEntity.ok(comicService.findAll(filterRequest.getField(), filterRequest.getAscending(), filterRequest.getPage(), filterRequest.getSize())
                .stream()
                .map(comicsMapper::entityToDto)
                .toList());
    }

    @Operation(summary = "Find by specific map filter")
    @PostMapping("findAll/map")
    public ResponseEntity<List<ComicDto>> findAllMap(@RequestBody Map<String, Object> filters, Pageable pageable) {
        return ResponseEntity.ok(comicService.findMapAll(filters, pageable)
                .stream()
                .map(comicsMapper::entityToDto)
                .toList());
    }

    @Operation(summary = "Update comic")
    @PutMapping("/update")
    public ResponseEntity<ComicDto> update(@RequestBody ComicDto comicDto) {
        return ResponseEntity.ok(comicsMapper.entityToDto(comicService.updateComic(comicDto)));
    }

    @Operation(summary = "Delete comic")
    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        return ResponseEntity.ok(comicService.delete(name));
    }

    @Operation(summary = "Delete all comics")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(comicService.deleteAll());
    }
}

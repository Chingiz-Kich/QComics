package kz.comics.account.controller.comics;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ComicsDto;
import kz.comics.account.service.ComicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/comics")
@RequiredArgsConstructor
public class ComicsController {

    private final ComicsService comicsService;

    @Operation(summary = "Save new comics")
    @PostMapping(path = "/save")
    public ResponseEntity<ComicsDto> save(@RequestBody ComicsDto comicsDto) {
        return ResponseEntity.ok(comicsService.saveComics(comicsDto));
    }

    @Operation(summary = "Get comics by name")
    @GetMapping("/{name}")
    public ResponseEntity<ComicsDto> getByName(@PathVariable String comicsName) {
        return ResponseEntity.ok(comicsService.getComics(comicsName));
    }
}

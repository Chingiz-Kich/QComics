package kz.comics.account.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Saving image")
    @PostMapping("/save")
    public ResponseEntity<ImageDto> save(@RequestBody ImageDto imageDto) {
        return ResponseEntity.ok(imageService.save(imageDto));
    }

    @Operation(summary = "Saving all images")
    @PostMapping("/saveAll")
    public ResponseEntity<List<ImageDto>> save(@RequestBody List<ImageDto> imageDtoList) {
        return ResponseEntity.ok(imageService.saveAll(imageDtoList));
    }

    @Operation(summary = "Get all images by name")
    @GetMapping("/getAll")
    public ResponseEntity<List<ImageDto>> save(@RequestParam String name) {
        return ResponseEntity.ok(imageService.getAllByName(name));
    }
}

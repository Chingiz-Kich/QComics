package kz.comics.account.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
import kz.comics.account.repository.entities.ImageEntity;
import kz.comics.account.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Get image by id")
    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(imageService.getById(id));
    }

    @Operation(summary = "Saving image")
    @PostMapping("/save")
    public ResponseEntity<ImageDto> save(@RequestBody ImageSaveDto imageSaveDto) {
        return ResponseEntity.ok(imageService.save(imageSaveDto));
    }

    @Operation(summary = "Saving all images")
    @PostMapping("/saveAll")
    public ResponseEntity<List<Integer>> save(@RequestBody List<ImageSaveDto> imageSaveDtos) {
        return ResponseEntity.ok(imageService.saveAll(imageSaveDtos));
    }

    @Operation(summary = "Get all images by chapter and comic name")
    @GetMapping("/all")
    public ResponseEntity<List<ImageDto>> save(@RequestParam String chapterName, @RequestParam String comicName) {
        return ResponseEntity.ok(imageService.getAllByChapterNameAndComicName(chapterName, comicName));
    }

    @Operation(summary = "Download image by id")
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        ImageEntity imageEntity = imageService.downloadById(id);
        byte[] value = imageEntity.getData();
        ByteArrayResource resource = new ByteArrayResource(value);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @Operation(summary = "Delete all images")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(imageService.deleteAll());
    }

    @Operation(summary = "Get IDs by chapter name and comic name")
    @GetMapping("/ids")
    public ResponseEntity<List<Integer>> getAllIdsByChapterAndComicName(@RequestParam String chapterName, @RequestParam String comicName) {
        return ResponseEntity.ok(imageService.getAllIdsByChapterAndComicName(chapterName, comicName));
    }
}

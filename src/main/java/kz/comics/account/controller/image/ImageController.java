package kz.comics.account.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
import kz.comics.account.repository.entities.ImageEntity;
import kz.comics.account.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@CrossOrigin
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
    public ResponseEntity<List<Integer>> savell(@RequestBody List<ImageSaveDto> imageSaveDtos) {
        return ResponseEntity.ok(imageService.saveAll(imageSaveDtos));
    }

    @Operation(summary = "Get all images by chapter and comic name")
    @GetMapping("/all")
    public ResponseEntity<List<ImageDto>> allByChapterNameAndComicName(@RequestParam String chapterName, @RequestParam String comicName) {
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

    @SneakyThrows
    @Operation(summary = "Download image by id")
    @GetMapping("/download/{chapterName}/{comicName}")
    public ResponseEntity<Resource> download(@PathVariable String chapterName, @PathVariable String comicName) {
        List<ImageEntity> imageEntities = imageService.downloadAll(chapterName, comicName); // Retrieve the list of images from your repository

        // Create a temporary ZIP file
        File zipFile = File.createTempFile(comicName + " - " + chapterName, ".zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(FileUtils.openOutputStream(zipFile));

        // Add each image file to the ZIP file
        for (ImageEntity image : imageEntities) {
            ZipEntry zipEntry = new ZipEntry(image.getId() + ".jpg"); // Customize the entry name as needed
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(image.getData());
            zipOutputStream.closeEntry();
        }

        // Close the ZIP output stream
        zipOutputStream.close();

        // Prepare the file resource from the ZIP file
        ByteArrayResource resource = new ByteArrayResource(FileUtils.readFileToByteArray(zipFile));

        // Delete the temporary ZIP file
        FileUtils.deleteQuietly(zipFile);

        // Create the response entity with the file resource and headers
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=images.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
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

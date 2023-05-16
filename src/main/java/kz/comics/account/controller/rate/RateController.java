package kz.comics.account.controller.rate;

import io.swagger.v3.oas.annotations.Operation;
import kz.comics.account.model.rate.RateDto;
import kz.comics.account.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @Operation(summary = "Save rate")
    @PostMapping("/save")
    public ResponseEntity<RateDto> saveRate(@RequestBody RateDto rateDto) {
        return ResponseEntity.ok(rateService.saveRate(rateDto.getComicName(), rateDto.getUsername(), rateDto.getRating()));
    }

    @Operation(summary = "Get rate")
    @GetMapping("")
    public ResponseEntity<Double> getRate(@RequestParam String comicName,  @RequestParam String username) {
        return ResponseEntity.ok(rateService.getRate(comicName, username));
    }

    @Operation(summary = "Update rate")
    @PutMapping("")
    public ResponseEntity<RateDto> updateRate(@RequestBody RateDto rateDto) {
        return ResponseEntity.ok(rateService.updateRate(rateDto.getComicName(), rateDto.getUsername(), rateDto.getRating()));
    }

    @Operation(summary = "Delete rate")
    @DeleteMapping("")
    public ResponseEntity<String> deleteRate(@RequestParam String comicName,  @RequestParam String username) {
        return ResponseEntity.ok(rateService.deleteRate(comicName, username));
    }
}

package com.sparta.travelshooting.S3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public List<String> saveImage(@ModelAttribute ImageSaveDto imageSaveDto) {
        return imageService.saveImages(imageSaveDto);
    }


    @PutMapping("/image/{imageId}")
    public ResponseEntity<String> updateImage(@PathVariable Long imageId, @RequestParam("file") MultipartFile file) {
        String imageUrl = imageService.updateImage(imageId, file);
        return ResponseEntity.ok(imageUrl);
    }


    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }


}

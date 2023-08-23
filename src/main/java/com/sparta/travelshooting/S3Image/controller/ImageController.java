package com.sparta.travelshooting.S3Image.controller;

import com.sparta.travelshooting.S3Image.dto.ImageSaveDto;
import com.sparta.travelshooting.S3Image.service.ImageService;
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

    //이미지 저장

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public List<String> saveImage(@ModelAttribute ImageSaveDto imageSaveDto) {
        return imageService.saveImages(imageSaveDto);
    }

    //이미지 수정
    @PutMapping("/image/{imageId}")
    public ResponseEntity<String> updateImage(@PathVariable Long imageId, @RequestParam("images") MultipartFile file) {
        String imageUrl = imageService.updateImage(imageId, file);
        return ResponseEntity.ok(imageUrl);
    }

    //이미지 삭제
    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }


}

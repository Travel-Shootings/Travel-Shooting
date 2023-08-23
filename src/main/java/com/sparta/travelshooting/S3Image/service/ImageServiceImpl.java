package com.sparta.travelshooting.S3Image.service;


import com.amazonaws.services.ecr.model.ImageNotFoundException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.travelshooting.S3Image.dto.ImageSaveDto;
import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.S3Image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static String bucketName = "travelshooting";

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;

    //이미지 파일을 업로드하고, 각 이미지의 업로드 된 URL을 리스트에 저장하여 반환
    @Override
    @Transactional
    public List<String> saveImages(ImageSaveDto saveDto) {
        List<String> resultList = new ArrayList<>();

        for(MultipartFile multipartFile : saveDto.getImages()) {
            String value = saveImage(multipartFile);
            resultList.add(value);
        }

        return resultList;
    }

    //이미지 파일을 S3에 업로드
    @Override
    @Transactional
    public String saveImage(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);
        String filename = image.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            image.setAccessUrl(accessUrl);
        } catch(IOException e) {

        }

        imageRepository.save(image);

        return image.getAccessUrl();
    }


    //이미지 수정
    @Override
    @Transactional
    public String updateImage(Long imageId, MultipartFile multipartFile) {
        Optional<Image> imageOptional = imageRepository.findById(imageId);
        if (imageOptional.isEmpty()) {
            // 이미지 정보가 없을 경우 예외 처리
            throw new ImageNotFoundException("Image not found with ID: " + imageId);
        }

        Image existingImage = imageOptional.get();

        // S3에서 기존 이미지 삭제
        deleteImageFromS3(existingImage.getStoredName());

        // S3에 새로운 이미지 저장
        String newImageUrl = saveImage(multipartFile);

        // 이미지 정보 업데이트
        existingImage.setAccessUrl(newImageUrl);
        imageRepository.save(existingImage);

        // 기존 이미지 정보 삭제
        imageRepository.delete(existingImage);

        return newImageUrl;
    }


    //이미지 삭제
    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID: " + imageId));

        // 이미지 삭제 로직
        deleteImageFromS3(image.getStoredName());

        // 데이터베이스에서 이미지 삭제
        imageRepository.delete(image);
    }

    //S3에서 이미지 삭제
    private void deleteImageFromS3(String filename) {
        amazonS3Client.deleteObject(bucketName, filename);
    }



}
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
        Image image = new Image(originalName, null);
        String filename = image.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            image.setAccessUrl(accessUrl);
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
        }
        return image.getAccessUrl();
    }


    //이미지 삭제
    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지를 찾을 수 없습니다: " + imageId));

        // S3에서 이미지 삭제 로직
        deleteImageFromS3(image.getAccessUrl());
        // 데이터베이스에서 이미지 삭제
        imageRepository.delete(image);
    }

    // S3에서 이미지 삭제
    private void deleteImageFromS3(String accessUrl) {
        String filename = extractFilenameFromUrl(accessUrl);
        amazonS3Client.deleteObject(bucketName, filename);
    }

    // URL에서 파일 이름 추출
    private String extractFilenameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            return url.substring(lastSlashIndex + 1);
        }
        return url;
    }

}

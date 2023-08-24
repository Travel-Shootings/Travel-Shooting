package com.sparta.travelshooting.S3Image.service;

import com.sparta.travelshooting.S3Image.dto.ImageSaveDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    //이미자파일 정보를 리스트에 저장
    List<String> saveImages(ImageSaveDto saveDto);

    //이미지 파일을 S3에 업로드
    String saveImage(MultipartFile multipartFile);

    //이미지 파일 수정
    String updateImage(Long imageId, MultipartFile multipartFile);

    //이미지 파일 S3에서 삭제
    void deleteImage(Long imageId);
}

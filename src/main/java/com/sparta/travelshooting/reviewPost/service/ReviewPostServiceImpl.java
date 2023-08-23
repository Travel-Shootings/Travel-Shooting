package com.sparta.travelshooting.reviewPost.service;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.S3Image.repository.ImageRepository;
import com.sparta.travelshooting.S3Image.service.ImageService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewPostServiceImpl implements ReviewPostService {

    private final ReviewPostRepository reviewPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public ReviewPostResponseDto createReviewPost(MultipartFile imageFile, ReviewPostRequestDto requestDto, User user) {
        // 이미지 업로드
        String imageUrl = imageService.saveImage(imageFile);

        //이미지 엔티티 생성
        Image image = new Image(imageFile.getOriginalFilename());
        image.setAccessUrl(imageUrl);
        imageRepository.save(image); // 이미지 엔티티를 저장

        // 게시글 생성 및 저장
        ReviewPost reviewPost = new ReviewPost(requestDto.getTitle(), requestDto.getContent(),user,image, image.getAccessUrl());
        reviewPostRepository.save(reviewPost);

        return new ReviewPostResponseDto(reviewPost);
    }


    @Override
    @Transactional
    public ApiResponseDto updateReviewPost(Long reviewPostId, MultipartFile imageFile, ReviewPostRequestDto requestDto, User user) {
        // 게시글 조회
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 업로드 및 수정
        String newImageUrl = null;
        Image newImage = null; // 새로운 이미지 엔티티 변수 추가

        if (imageFile != null && !imageFile.isEmpty()) {
            newImageUrl = imageService.updateImage(reviewPost.getImage().getId(), imageFile);
            newImage = new Image(imageFile.getOriginalFilename()); // 새로운 이미지 엔티티 생성 코드 추가
            imageRepository.save(newImage); // 새로운 이미지를 저장해야 함
        } else {
            newImage = reviewPost.getImage(); // 이미지 파일이 없을 때 기존 이미지 사용
            newImageUrl = newImage.getAccessUrl(); // 이미지 파일이 없을 때도 accessUrl 사용
        }

        // 게시글 업데이트
        reviewPost.updateReviewPost(requestDto.getTitle(), requestDto.getContent(), newImage, newImageUrl);

        return new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value());
    }


    @Override
    @Transactional
    public ApiResponseDto deleteReviewPost(Long reviewPostId, User user) {
        // 게시글 조회
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 삭제
        if (reviewPost.getImage() != null) {
            imageService.deleteImage(reviewPost.getImage().getId());
        }

        // 게시글 삭제
        reviewPostRepository.delete(reviewPost);

        return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
    }
}




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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Image image = new Image(imageFile.getOriginalFilename());
        String imageUrl = imageService.saveImage(imageFile);
        image.setAccessUrl(imageUrl);
//

        // 게시글 생성 및 저장
        ReviewPost reviewPost = new ReviewPost(requestDto.getTitle(), requestDto.getContent(),user, image);
        reviewPostRepository.save(reviewPost);

        return new ReviewPostResponseDto(reviewPost);
    }

    @Override
    public ReviewPostResponseDto getReviewPost(Long reviewPostId) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();
        return new ReviewPostResponseDto(reviewPost);
    }

    @Override
    public List<ReviewPostResponseDto> getAllReviewPosts() {
        List<ReviewPost> reviewPosts = reviewPostRepository.findAll();
        return reviewPosts.stream()
                .map(ReviewPostResponseDto::new)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public ApiResponseDto updateReviewPost(Long reviewPostId, MultipartFile imageFile, ReviewPostRequestDto requestDto, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 업데이트
        if (imageFile != null) {
            Image image = reviewPost.getImage();
            if (image != null) {
                String newImageUrl = imageService.updateImage(image.getId(), imageFile);
                image.setAccessUrl(newImageUrl);
                imageRepository.save(image);
            }
        }

        // 게시글 정보 업데이트 (제목, 내용)
        reviewPost.updateReviewPost(requestDto.getTitle(), requestDto.getContent(), reviewPost.getImage());

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




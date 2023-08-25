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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewPostServiceImpl implements ReviewPostService {

    private final ReviewPostRepository reviewPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;


    //후기 게시글 생성
    @Override
    @Transactional
    public ReviewPostResponseDto createReviewPost(MultipartFile imageFile, ReviewPostRequestDto requestDto, User user) {
        Image image = null;  // 이미지 초기화

        if (imageFile != null && !imageFile.isEmpty()) {
            image = new Image(imageFile.getOriginalFilename());
            String imageUrl = imageService.saveImage(imageFile);
            image.setAccessUrl(imageUrl);
        }

        ReviewPost reviewPost = new ReviewPost(requestDto.getTitle(), requestDto.getContent(), user, image);
        reviewPostRepository.save(reviewPost);

        return new ReviewPostResponseDto(reviewPost);
    }

    //후기 게시글 수정


    @Override
    @Transactional
    public ApiResponseDto updateReviewPost(Long reviewPostId, MultipartFile imageFile, ReviewPostRequestDto requestDto, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 업데이트
        Image image = reviewPost.getImage();
        if (imageFile != null) {
            if (image != null) {
                // 기존 이미지가 있는 경우 업데이트
                String newImageUrl = imageService.updateImage(image.getId(), imageFile);
                image.setAccessUrl(newImageUrl);
                imageRepository.save(image);
            } else {
                // 기존 이미지가 없는 경우 새 이미지 추가
                Image newImage = new Image(imageFile.getOriginalFilename());
                String newImageUrl = imageService.saveImage(imageFile);
                newImage.setAccessUrl(newImageUrl);
                imageRepository.save(newImage);
                reviewPost.setImage(newImage); // ReviewPost와 연결
            }
        } else {
            // 이미지를 null로 넣은 경우 기존 이미지를 삭제
            if (image != null) {
                // 이미지 관련 로직 추가 (엔티티와 S3에서 삭제)
                imageService.deleteImage(image.getId()); // 이미지 삭제 서비스 메서드 호출
                reviewPost.setImage(null); // ReviewPost에서 이미지 연결 제거
            }
        }

        // 게시글 정보 업데이트 (제목, 내용)
        reviewPost.updateReviewPost(requestDto.getTitle(), requestDto.getContent(), reviewPost.getImage());

        return new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value());
    }


    //후기 게시글 삭제
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


    //게시글 단건 조회
    @Override
    public ReviewPostResponseDto getReviewPost(Long reviewPostId) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();
        return new ReviewPostResponseDto(reviewPost);
    }

    //게시글 전체 조회
    @Override
    public List<ReviewPostResponseDto> getAllReviewPosts() {
        List<ReviewPost> reviewPosts = reviewPostRepository.findAll();
        return reviewPosts.stream()
                .map(ReviewPostResponseDto::new)
                .collect(Collectors.toList());
    }
}




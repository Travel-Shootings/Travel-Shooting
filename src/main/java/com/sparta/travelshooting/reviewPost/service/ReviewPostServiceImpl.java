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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewPostServiceImpl implements ReviewPostService {

    private final ReviewPostRepository reviewPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;


    // 후기 게시글 생성
    @Override
    @Transactional
    public ReviewPostResponseDto createReviewPost(List<MultipartFile> imageFiles, ReviewPostRequestDto requestDto, User user) {
        List<Image> images = new ArrayList<>();  // 이미지 초기화

        for (MultipartFile imageFile : imageFiles) {
            if (imageFile != null && !imageFile.isEmpty()) {
                Image image = new Image(imageFile.getOriginalFilename(), null); // ReviewPost는 아직 생성되지 않았으므로 null 전달
                String imageUrl = imageService.saveImage(imageFile);
                image.setAccessUrl(imageUrl);
                images.add(image); // 이미지 컬렉션에 추가
            }
        }

        ReviewPost reviewPost = new ReviewPost(requestDto.getTitle(), requestDto.getContent(), user, images);
        reviewPostRepository.save(reviewPost);

        // 이미지 객체에 후기 게시글 객체 설정
        if (!images.isEmpty()) {
            for (Image image : images) {
                image.setReviewPost(reviewPost);
            }
        }

        return new ReviewPostResponseDto(reviewPost);
    }

    // 후기 게시글 수정
    @Override
    @Transactional
    public ApiResponseDto updateReviewPost(Long reviewPostId, List<MultipartFile> imageFiles, ReviewPostRequestDto requestDto, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 업데이트
        List<Image> images = reviewPost.getImages();
        if (imageFiles != null && !imageFiles.isEmpty()) {


            for (MultipartFile imageFile : imageFiles) {
                Image newImage = new Image(imageFile.getOriginalFilename(), reviewPost);
                String newImageUrl = imageService.saveImage(imageFile);
                newImage.setAccessUrl(newImageUrl);
                imageRepository.save(newImage);
                images.add(newImage);
            }
        } else {
            // 이미지를 null로 넣은 경우 기존 이미지 삭제
            if (!images.isEmpty()) {
                for (Image image : images) {
                    imageService.deleteImage(image.getId()); // 이미지 삭제 서비스 메서드 호출
                }

            }
        }

        // 게시글 정보 업데이트 (제목, 내용)
        reviewPost.updateReviewPost(requestDto.getTitle(), requestDto.getContent(), images);

        return new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value());
    }


    // 후기 게시글 삭제
    @Override
    @Transactional
    public ApiResponseDto deleteReviewPost(Long reviewPostId, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        // 이미지 삭제
        List<Image> images = reviewPost.getImages();
        for (Image image : images) {
            imageService.deleteImage(image.getId());
        }
        images.clear(); // 이미지 컬렉션 초기화

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




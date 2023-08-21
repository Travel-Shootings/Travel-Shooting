package com.sparta.travelshooting.reviewPost.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

@Service
@AllArgsConstructor
public class ReviewPostServiceImpl implements ReviewPostService {

    private final ReviewPostRepository reviewPostRepository;

    @Override
    public ReviewPostResponseDto createReviewPost(MultipartFile imageFile, ReviewPostRequestDto requestDto, User user){


        String imageUrl = saveImage(imageFile); // 이미지를 저장하고 URL을 반환하는 메서드

        ReviewPost reviewPost = new ReviewPost(requestDto.getTitle(),requestDto.getContent(),user,imageUrl);
        reviewPostRepository.save(reviewPost);

        return new ReviewPostResponseDto(reviewPost);
    }

    @Override
    public ApiResponseDto updateReviewPost(Long ReviewPostId, MultipartFile imageFile, ReviewPostRequestDto requestDto, User user){
        ReviewPost reviewPost = reviewPostRepository.findById(ReviewPostId).orElseThrow(()->new IllegalArgumentException("해당 게시글을 찾을 수 없습니다"));
        if (!reviewPost.getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 수정 가능합니다");
        }

        if (imageFile != null && !imageFile.isEmpty()){
            String imageUrl = saveImage(imageFile);
            reviewPost.setImageUrl(imageUrl);
        }
        reviewPost.updateReviewPost(requestDto.getTitle(),requestDto.getContent());

        reviewPostRepository.save(reviewPost);

        return new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value());
    }








    private String saveImage(MultipartFile imageFile){
        try{// 이미지를 저장할 디렉토리 경로 설정
            String uploadDirectory = "/path/to/upload/directory/";

            // 이미지 파일의 원본 파일명 가져오기
            String originalFilename = imageFile.getOriginalFilename();

            // 이미지 파일을 저장할 실제 경로 설정
            String filePath = uploadDirectory + originalFilename;

            // 이미지 파일 저장
            imageFile.transferTo(new File(filePath));

            // 저장된 이미지 파일의 URL 생성
            String imageUrl = "http://example.com/images/" + originalFilename;

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.");
        }

        }
    }




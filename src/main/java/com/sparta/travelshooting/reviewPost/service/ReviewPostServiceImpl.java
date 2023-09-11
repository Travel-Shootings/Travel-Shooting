package com.sparta.travelshooting.reviewPost.service;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.S3Image.repository.ImageRepository;
import com.sparta.travelshooting.S3Image.service.ImageService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.post.dto.PostListResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.reviewPost.dto.HomeReviewResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostListResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.entity.ReviewPostLike;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostLikeRepository;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostRepository;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final ReviewPostLikeRepository reviewPostLikeRepository;


    // 후기 게시글 생성
    @Override
    @Transactional
    public ApiResponseDto createReviewPost(List<MultipartFile> imageFiles, ReviewPostRequestDto requestDto, User user) {
        List<Image> images = new ArrayList<>();  // 이미지 초기화

        // title과 content가 모두 유효한 값인지 확인
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty() ||
                requestDto.getContent() == null || requestDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("Title and content must not be empty.");
        }

        // imageFiles가 null이 아니고 비어있지 않은 경우에만 처리
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile imageFile : imageFiles) {
                if (imageFile != null && !imageFile.isEmpty()) {
                    Image image = new Image(imageFile.getOriginalFilename(), null); // ReviewPost는 아직 생성되지 않았으므로 null 전달
                    String imageUrl = imageService.saveImage(imageFile);
                    image.setAccessUrl(imageUrl);
                    images.add(image); // 이미지 컬렉션에 추가
                }
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
        return  new ApiResponseDto("게시글이 생성되었습니다.", HttpStatus.CREATED.value());
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

        if (!reviewPost.getUser().getId().equals(user.getId())) {
            return new ApiResponseDto("게시글 작성자만 수정할 수 있습니다.", HttpStatus.FORBIDDEN.value());
        }

        String newTitle = requestDto.getTitle();
        String newContent = requestDto.getContent();

        if (newTitle == null || newTitle.trim().isEmpty() || newContent == null || newContent.trim().isEmpty()) {
            return new ApiResponseDto("제목과 내용을 입력해주세요.", HttpStatus.BAD_REQUEST.value());
        }

        // 기존 이미지 삭제
        List<Image> existingImages = reviewPost.getImages();
        for (Image existingImage : existingImages) {
            imageService.deleteImage(existingImage.getId()); // 이미지 삭제 서비스 메서드 호출
        }
        existingImages.clear(); // 기존 이미지 목록 비우기

        // 새로운 이미지 추가
        List<Image> updatedImages = new ArrayList<>();
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile imageFile : imageFiles) {
                Image newImage = new Image(imageFile.getOriginalFilename(), reviewPost);
                String newImageUrl = imageService.saveImage(imageFile);
                newImage.setAccessUrl(newImageUrl);
                imageRepository.save(newImage);
                updatedImages.add(newImage);
            }
        }
        // 게시글 정보 업데이트 (제목, 내용)
        reviewPost.updateReviewPost(requestDto.getTitle(), requestDto.getContent(), updatedImages);

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

        if (!reviewPost.getUser().getId().equals(user.getId()) && String.valueOf(user.getRole()).equals("USER")) {
            return new ApiResponseDto("게시글 작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN.value());
        }

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


    //최근 6개 게시글 조회(Home화면)

    @Override
    public List<HomeReviewResponseDto> getSixReview() {
        List<ReviewPost> reviewPostList = reviewPostRepository.findTop6ByOrderByCreatedAtDesc();

        return reviewPostList.stream()
                .map(HomeReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    //게시글 전체 조회
    @Override
    public List<ReviewPostListResponseDto> getAllReviewPosts() {
        List<ReviewPost> reviewPosts = reviewPostRepository.findAll();
        return reviewPosts.stream()
                .map(ReviewPostListResponseDto::new)
                .collect(Collectors.toList());
    }

    //게시물 페이지 조회
    @Override
    public Page<ReviewPostListResponseDto> getPageReviewPosts(Pageable pageable) {
        List<ReviewPost> reviewPosts = reviewPostRepository.findAllByOrderByCreatedAtDesc();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reviewPosts.size());

        Page<ReviewPost> pageReviewPosts = new PageImpl<>(reviewPosts.subList(start, end), pageable, reviewPosts.size());

        return pageReviewPosts.map(ReviewPostListResponseDto::new);
    }

    //좋아요 기능
    @Override
    @Transactional
    public ApiResponseDto addLike(Long reviewPostId, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        Optional<ReviewPostLike> findReviewPostLike = reviewPostLikeRepository.findByReviewPostIdAndUserId(reviewPostId, user.getId());
        if (reviewPost.getUser().getId().equals(user.getId())) {
            return new ApiResponseDto("자신의 글에는 좋아요를 할 수 없습니다.", 400);
        } else if (findReviewPostLike.isPresent()) {
            return new ApiResponseDto("이미 좋아요를 한 상태입니다.", 400);
        }

        ReviewPostLike newReviewPostLike = new ReviewPostLike(user, reviewPost);
        reviewPostLikeRepository.save(newReviewPostLike);

        reviewPost.setLikeCounts(reviewPost.getLikeCounts() + 1);
        reviewPostRepository.save(reviewPost);

        return new ApiResponseDto("좋아요 등록 성공", 200);
    }

    //좋아요 취소
    @Override
    @Transactional
    public ApiResponseDto deleteLike(Long reviewPostId, User user) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        if (optionalReviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        ReviewPost reviewPost = optionalReviewPost.get();

        Optional<ReviewPostLike> findReviewPostLike = reviewPostLikeRepository.findByReviewPostIdAndUserId(reviewPostId, user.getId());
        if (findReviewPostLike.isEmpty()) {
            return new ApiResponseDto("해당 글에 좋아요를 하지 않은 상태입니다.", 400);
        }

        reviewPostLikeRepository.delete(findReviewPostLike.get());
        reviewPost.setLikeCounts(reviewPost.getLikeCounts() - 1);
        reviewPostRepository.save(reviewPost);

        return new ApiResponseDto("좋아요 취소 성공", 200);
    }

    //좋아요 여부 조회
    @Override
    public boolean hasLiked(Long reviewPostId, Long userId) {
        return reviewPostLikeRepository.findByReviewPostIdAndUserId(reviewPostId, userId).isPresent();
    }
    //작성자 확인
    @Override
    public boolean reviewPostCheckUser(UserDetailsImpl currentUser, Long reviewPostId) {
        Optional<ReviewPost> optionalReviewPost = reviewPostRepository.findById(reviewPostId);
        return optionalReviewPost.map(reviewPost -> reviewPost.getUser().getId().equals(currentUser.getUser().getId()))
                .orElse(false);
    }

}




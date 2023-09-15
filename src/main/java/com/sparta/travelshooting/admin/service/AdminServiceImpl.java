package com.sparta.travelshooting.admin.service;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.S3Image.repository.ImageRepository;
import com.sparta.travelshooting.S3Image.service.ImageService;
import com.sparta.travelshooting.admin.dto.AdminCommentRequestDto;
import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.entity.JourneyList;
import com.sparta.travelshooting.journeylist.repository.JourneyListRepository;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reply.entity.Reply;
import com.sparta.travelshooting.reply.repository.ReplyRepository;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostRepository;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JourneyListRepository journeyListRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final ReviewPostRepository reviewPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;


    //총 유저 수, 총 게시글 수 , 총 댓글 수 조회
    @Override
    public AdminSummaryResponseDto showSummary() {
        return new AdminSummaryResponseDto(userRepository.count(),
                postRepository.count() + reviewPostRepository.count(),
                commentRepository.count()
        );
    }

    // 전체 유저 정보 조회 -> 추후 QueryDsl로 수정 예정
    @Override
    public List<UserResponseDto> showUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    // 글 전체 조회
    @Override
    public List<PostResponseDto> showPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewPostResponseDto> showReviewPosts() {
        return reviewPostRepository.findAll().stream()
                .map(ReviewPostResponseDto::new)
                .toList();
    }

    // 유저 정보 수정
    @Override
    @Transactional
    public ApiResponseDto updateUser(Long userId, AdminProfileRequestDto requestDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NullPointerException("해당 유저는 존재하지 않습니다.");
        }
        user.get().updateByAdmin(requestDto);
        return new ApiResponseDto("유저 정보 수정 성공", HttpStatus.OK.value());
    }

    // 게시글 수정
    @Transactional
    @Override
    public ApiResponseDto updatePost(Long postId, PostRequestDto requestDto) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        }
        post.get().update(requestDto);
        return new ApiResponseDto("게시글 수정 성공", HttpStatus.OK.value());
    }

    // 여행 일정 수정
    @Transactional
    @Override
    public ApiResponseDto updateJourneyList(Long journeyListId, JourneyListRequestDto requestDto) {
        Optional<JourneyList> journeyList = journeyListRepository.findById(journeyListId);
        if (journeyList.isEmpty()) {
            throw new NullPointerException("해당 여행 일정은 존재하지 않습니다.");
        }
        journeyList.get().updateByAdmin(requestDto);
        return new ApiResponseDto("여행일정 수정 성공", HttpStatus.OK.value());
    }

    //댓글 수정
    @Transactional
    @Override
    public ApiResponseDto updateComment(Long commentId, AdminCommentRequestDto requestDto) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new NullPointerException("해당 댓글은 존재하지 않습니다.");
        }
        comment.get().updateByAdmin(requestDto);
        return new ApiResponseDto("댓글 수정 성공", HttpStatus.OK.value());
    }

    //대댓글 수정
    @Transactional
    @Override
    public ApiResponseDto updateReply(Long replyId, ReplyRequestDto requestDto) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        if (reply.isEmpty()) {
            throw new NullPointerException("해당 대댓글은 존재하지 않습니다.");
        }
        reply.get().updateByAdmin(requestDto);
        return new ApiResponseDto("대댓글 수정 성공", HttpStatus.OK.value());
    }

    //후기 게시글 수정
    @Transactional
    @Override
    public ApiResponseDto updateReviewPost(Long reviewPostId, ReviewPostRequestDto requestDto, List<MultipartFile> imageFiles) {
        Optional<ReviewPost> findReviewPost = reviewPostRepository.findById(reviewPostId);
        if (findReviewPost.isEmpty()) {
            throw new NullPointerException("해당 후기 게시글은 존재하지 않습니다.");
        }
        ReviewPost reviewPost = findReviewPost.get();

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

        reviewPost.updateByAdmin(requestDto, images);
        return new ApiResponseDto("후기 게시글 수정 성공", HttpStatus.OK.value());
    }

    //유저 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NullPointerException("해당 유저는 존재하지 않습니다.");
        }
        userRepository.delete(user.get());
        return new ApiResponseDto("유저 삭제 성공", HttpStatus.OK.value());
    }

    //게시글 삭제
    @Transactional
    @Override
    public ApiResponseDto deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        }
        postRepository.delete(post.get());
        return new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
    }


    //여행 일정 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteJourneyList(Long journeyListId) {
        Optional<JourneyList> journeyList = journeyListRepository.findById(journeyListId);
        if (journeyList.isEmpty()) {
            throw new NullPointerException("해당 여행 일정은 존재하지 않습니다.");
        }
        journeyListRepository.delete(journeyList.get());
        return new ApiResponseDto("여행일정 삭제 성공", HttpStatus.OK.value());
    }

    //댓글 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new NullPointerException("해당 댓글은 존재하지 않습니다.");
        }
        commentRepository.delete(comment.get());
        return new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
    }

    //대댓글 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        if (reply.isEmpty()) {
            throw new NullPointerException("해당 대댓글은 존재하지 않습니다.");
        }
        replyRepository.delete(reply.get());
        return new ApiResponseDto("대댓글 삭제 성공", HttpStatus.OK.value());
    }

    //후기 글 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteReviewPost(Long reviewPostId) {
        Optional<ReviewPost> reviewPost = reviewPostRepository.findById(reviewPostId);
        if (reviewPost.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        }

        List<Image> images = reviewPost.get().getImages();
        for (Image image : images) {
            imageService.deleteImage(image.getId());
        }
        images.clear();

        reviewPostRepository.delete(reviewPost.get());
        return new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
    }

    @Override
    public UserResponseDto showUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당 유저가 존재하지 않습니다."));
        return new UserResponseDto(user);
    }
}


package com.sparta.travelshooting.comment.service;

import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.notification.entity.Notification;
import com.sparta.travelshooting.notification.repository.NotificationRepository;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.repository.ReviewPostRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final ReviewPostRepository reviewPostRepository;

    public static final String COMMENT_CREATE_MESSAGE = " 님이 댓글을 작성했습니다.";
    public static final String COMMENT_MESSAGE = " 댓글 내용 : ";


    //여행 게시판 댓글 생성
    @Override
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(requestDto, post, null, user);
        commentRepository.save(comment);

        // 알림 보내기 (게시글 작성자와 다른 사람이 댓글을 달았을 경우에만 게시글 작성자에게 알림)
        User author = post.getUser(); // post 작성자
        if (!author.getNickname().equals(user.getNickname())) {

            String comment_content = comment.getContent();
            if (comment_content.length() > 20) {
                comment_content = comment_content.substring(0, 20) + "...";
            }

            String message = "여행계획 게시글 : " + post.getTitle() + " 에 " + user.getNickname() + COMMENT_CREATE_MESSAGE + COMMENT_MESSAGE + comment_content; // 알림 메세지
            boolean read = false; // 알림 확인 여부

            Notification notification = new Notification(author, message, read, post.getId(), null);
            notificationRepository.save(notification);
        }

        return new CommentResponseDto(comment);
    }


    //후기 게시판 댓글 생성
    @Override
    public CommentResponseDto createCommentReview(Long ReviewPostId, CommentRequestDto requestDto, User user) {
        ReviewPost reviewPost = reviewPostRepository.findById(ReviewPostId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(requestDto, null, reviewPost, user);
        commentRepository.save(comment);

        // 알림 보내기 (게시글 작성자와 다른 사람이 댓글을 달았을 경우에만 게시글 작성자에게 알림)
        User author = reviewPost.getUser(); // review post 작성자

        if (!author.getNickname().equals(user.getNickname())) {
            String comment_content = comment.getContent();
            if (comment_content.length() > 20) {
                comment_content = comment_content.substring(0, 20) + "...";
            }

            String message = "여행후기 게시글 : " + reviewPost.getTitle() + " 에 " + user.getNickname() + COMMENT_CREATE_MESSAGE + COMMENT_MESSAGE + comment_content; // 알림 메세지
            boolean read = false; // 알림 확인 여부

            Notification notification = new Notification(author, message, read, null, reviewPost.getId());
            notificationRepository.save(notification);
            log.info("알림 저장 완료");
        }

        return new CommentResponseDto(comment);
    }

    @Override
    //댓글 수정
    public ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException("작성자만 수정 가능합니다");
        }
        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);
        return new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value());
    }

    //댓글 삭제
    @Override
    public ApiResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException("작성자만 삭제 가능합니다");
        }
        commentRepository.delete(comment);
        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }


    // 여행 계획 게시물의 댓글 조회 메서드
    public List<CommentResponseDto> getCommentsForPost(Long postId) {
        // 여행 계획 게시물에 대한 댓글을 데이터베이스에서 조회
        List<Comment> comments = commentRepository.findByPostId(postId);
        return  comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());
    }


    // 리뷰 게시물의 댓글 조회 메서드
    public List<CommentResponseDto> getCommentsForReviewPost(Long reviewPostId) {
        // 리뷰 게시물에 대한 댓글을 데이터베이스에서 조회
        List<Comment> comments = commentRepository.findByReviewPostId(reviewPostId);
        // Comment 엔티티를 CommentResponseDto로 변환
        return  comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());

    }


}



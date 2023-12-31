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

import java.util.concurrent.RejectedExecutionException;

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
            sendNotification(post, null, user, comment.getContent(), "여행계획 게시글 : ");
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
            sendNotification(null, reviewPost, user, comment.getContent(), "여행후기 게시글 : ");
        }

        return new CommentResponseDto(comment);
    }

    @Override
    //댓글 수정
    public ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RejectedExecutionException("댓글 수정 권한이 없습니다");
        }
        comment.updateContent(requestDto.getContent());
        commentRepository.save(comment);
        return new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value());
    }

    //댓글 삭제
    @Override
    public ApiResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        if (!comment.getUser().getId().equals(user.getId()) && String.valueOf(user.getRole()).equals("USER")) {
            throw new RejectedExecutionException("댓글 삭제 권한이 없습니다");
        }
        commentRepository.delete(comment);
        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    // 댓글 작성 알림 보내기
    public void sendNotification(Post post, ReviewPost reviewPost, User user, String content, String idx) {
        if (content.length() > 20) {
            content = content.substring(0, 20) + "...";
        }

        String title;
        if (post == null) {
            title = reviewPost.getTitle();
        } else {
            title = post.getTitle();
        }

        String message = idx + title + " 에 " + user.getNickname() + COMMENT_CREATE_MESSAGE + COMMENT_MESSAGE + content; // 알림 메세지
        boolean read = false; // 알림 확인 여부

        Notification notification;
        if (post != null) {
            notification = new Notification(post.getUser(), message, read, post.getId(), null);
        } else {
            notification = new Notification(reviewPost.getUser(), message, read, null, reviewPost.getId());
        }
        notificationRepository.save(notification);
    }
}



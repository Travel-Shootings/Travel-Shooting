package com.sparta.travelshooting.comment.service;

import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
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



    @Override
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(requestDto, post, user);
        commentRepository.save(comment);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    @Override
    //댓글 수정
    public ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 수정 가능합니다");
        }
        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);
        return new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value());
    }

    //댓글 삭제
    @Override
    public ApiResponseDto deleteComment(Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        if(!comment.getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 삭제 가능합니다");
        }
        commentRepository.delete(comment);
        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }
}

package com.sparta.travelshooting.comment.service;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;



    //댓글 생성
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user){
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow();
        Comment comment = new Comment(requestDto, post, user);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    //댓글 수정
    public void updateComment(Long commentId, CommentRequestDto requestDto, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser.getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 수정 가능합니다");
        }
        comment.setContents(requestDto.getContents());
        commentRepository.save(comment);
    }

    //댓글 삭제

    public void deleteComment(Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        if(!comment.getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 삭제 가능합니다");
        }
        commentRepository.delete(comment);
    }


}

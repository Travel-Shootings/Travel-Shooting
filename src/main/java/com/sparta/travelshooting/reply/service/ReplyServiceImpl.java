package com.sparta.travelshooting.reply.service;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reply.dto.ReplyResponseDto;
import com.sparta.travelshooting.reply.entity.Reply;
import com.sparta.travelshooting.reply.repository.ReplyRepository;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;


    //대댓글 생성
    @Override
    public ReplyResponseDto createReply(Long commentId, ReplyRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));
        Reply reply = new Reply(requestDto, comment, user);
        replyRepository.save(reply);

        return new ReplyResponseDto(reply);
    }

    //대댓글 수정
    @Override
    public ApiResponseDto updateReply(Long replyId, ReplyRequestDto replyRequestDto, User user) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("대댓글을 찾을 수 없습니다."));

        if (!reply.getUser().getId().equals(user.getId())) {
            return new ApiResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.FORBIDDEN.value());
        }

        reply.setContent(replyRequestDto.getContent());

        replyRepository.save(reply);
        return new ApiResponseDto("대댓글 수정 완료", HttpStatus.OK.value());
    }

    //대댓글 삭제
    @Override
    public ApiResponseDto deleteReply(Long replyId, User user) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("대댓글을 찾을 수 없습니다."));

        if (!reply.getUser().getId().equals(user.getId())) {
            return new ApiResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN.value());
        }

        replyRepository.delete(reply);
        return new ApiResponseDto("대댓글 삭제 완료", HttpStatus.OK.value());
    }

}

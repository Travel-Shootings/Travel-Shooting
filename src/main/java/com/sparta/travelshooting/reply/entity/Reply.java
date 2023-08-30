package com.sparta.travelshooting.reply.entity;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_Id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    private String content;


    public Reply(ReplyRequestDto replyRequestDto,Comment comment, User user){
        this.user = user;
        this.comment = comment;
        this.content = replyRequestDto.getContent();
    }

    public void updateByAdmin (ReplyRequestDto replyRequestDto) {
        this.content = replyRequestDto.getContent();
    }


}

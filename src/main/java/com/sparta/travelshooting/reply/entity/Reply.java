package com.sparta.travelshooting.reply.entity;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.Timestamp;
import com.sparta.travelshooting.post.entity.Post;
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
public class Reply extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String content;


    public Reply(ReplyRequestDto replyRequestDto,Comment comment, User user){

        this.user = user;
        this.comment = comment;
        this.content = replyRequestDto.getContent();
    }


}

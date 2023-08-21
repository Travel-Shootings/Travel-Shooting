package com.sparta.travelshooting.comment.entity;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.common.Timestamp;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name= "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    public Comment(CommentRequestDto commentRequestDto, Post post, User user){
        this.nickName = user.getNickname();
        this.content = commentRequestDto.getContent();
        this.post = post;
    }

}

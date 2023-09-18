package com.sparta.travelshooting.comment.entity;


import com.sparta.travelshooting.admin.dto.AdminCommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.reply.entity.Reply;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "review_post_id")
    private ReviewPost reviewPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();


    public Comment(CommentRequestDto commentRequestDto, Post post, ReviewPost reviewPost, User user) {
        this.user = user;
        this.nickName = user.getNickname();
        this.content = commentRequestDto.getContent();
        this.post = post;
        this.reviewPost = reviewPost;
    }


    public void updateContent(String content) {
        this.content = content;
    }


    public void updateByAdmin(AdminCommentRequestDto commentRequestDto) {
        this.nickName = commentRequestDto.getNickname();
        this.content = commentRequestDto.getContents();
    }
}

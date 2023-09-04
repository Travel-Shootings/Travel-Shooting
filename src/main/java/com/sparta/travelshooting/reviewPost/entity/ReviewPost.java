package com.sparta.travelshooting.reviewPost.entity;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.post.entity.PostLike;
import com.sparta.travelshooting.reply.entity.Reply;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ReviewPost")

public class ReviewPost extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "reviewPost", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>(); // 이미지 컬렉션 추가

    @OneToMany(mappedBy = "reviewPost", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Column
    private Integer likeCounts = 0;

    @Column(nullable = false)
    private String nickName;

    @OneToMany(mappedBy = "reviewPost", cascade = CascadeType.REMOVE)
    private List<ReviewPostLike> reviewPostLikes = new ArrayList<>();

    public ReviewPost(String title, String content, User user, List<Image> images) {

        this.title = title;
        this.content = content;
        this.user = user;
        this.nickName = user.getNickname();
        this.images = images;
    }

    public void updateReviewPost(String title, String content, List<Image> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public void updateByAdmin(ReviewPostRequestDto requestDto, List<Image> images) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.images = images;
    }


}

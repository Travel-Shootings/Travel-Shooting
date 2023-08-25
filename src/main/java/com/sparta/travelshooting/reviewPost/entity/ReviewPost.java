package com.sparta.travelshooting.reviewPost.entity;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.common.Timestamped;
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
@Table(name ="ReviewPost")

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





    public ReviewPost(String title, String content, User user,List<Image> images) {

        this.title = title;
        this.content = content;
        this.user = user;
        this.images = images;


    }

    public void updateReviewPost(String title, String content, List<Image> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }




}

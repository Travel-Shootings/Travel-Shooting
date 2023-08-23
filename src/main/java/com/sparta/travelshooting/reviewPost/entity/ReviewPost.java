package com.sparta.travelshooting.reviewPost.entity;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Image와의 관계 매핑
    @JoinColumn(name = "imageId")
    private Image image;


    private String accessUrl;


    public ReviewPost(String title, String content, User user, Image image,String accessUrl) {

        this.title = title;
        this.content = content;
        this.user = user;
        this.image = image;
        this.accessUrl = accessUrl;

    }

    public void updateReviewPost(String title, String content, Image image,String accessUrl) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.image.setAccessUrl(accessUrl);
    }
}

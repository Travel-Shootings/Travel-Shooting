package com.sparta.travelshooting.reviewPost.entity;

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

public class ReviewPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String imageUrl;


    public ReviewPost(String title, String content, User user, String imageUrl) {

        this.title = title;
        this.content = content;
        this.user = user;
        this.imageUrl = imageUrl;
    }

    public void updateReviewPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

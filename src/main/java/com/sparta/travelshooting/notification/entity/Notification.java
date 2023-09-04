package com.sparta.travelshooting.notification.entity;

import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column
    private Boolean isRead;

    @Column
    private Long postId;

    @Column
    private Long reviewPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(User author, String message, boolean read, Long postId, Long reviewPostId) {
        this.message = message;
        this.user = author;
        this.isRead = read;
        this.postId = postId;
        this.reviewPostId = reviewPostId;
    }

    public void read() {
        this.isRead = true;
    }
}

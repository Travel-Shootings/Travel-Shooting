package com.sparta.travelshooting.post.entity;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.journeylist.entity.JourneyList;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.reply.entity.Reply;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column
    private Integer likeCounts = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<JourneyList> journeyLists = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> posts = new ArrayList<>();

    public Post (PostRequestDto postRequestDto, List<JourneyList> journeyList, User user) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.journeyLists = journeyList;
        this.user = user;
    }

    public void update (PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}

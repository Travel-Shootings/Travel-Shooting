package com.sparta.travelshooting.S3Image.entity;

import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_post_id")
    private ReviewPost reviewPost;

    private String originName; // 이미지 파일의 본래 이름

    private String storedName; // 이미지 파일이 S3에 저장될때 사용되는 이름

    private String accessUrl; // S3 내부 이미지에 접근할 수 있는 URL

    public Image(String originName, ReviewPost reviewPost) {
        this.originName = originName;
        this.storedName = getFileName(originName);
        this.accessUrl = "";
        this.reviewPost = reviewPost;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
        updateStoredName();
    }

    public void setReviewPost(ReviewPost reviewPost) {
        this.reviewPost = reviewPost;
    }


    private void updateStoredName() {
        this.storedName = getFileName(originName);
    }

    // 이미지 파일의 확장자를 추출하는 메소드
    public String extractExtension(String originName) {
        int index = originName.lastIndexOf('.');

        return originName.substring(index, originName.length());
    }

    // 이미지 파일의 이름을 저장하기 위한 이름으로 변환하는 메소드
    public String getFileName(String originName) {
        return UUID.randomUUID() + "." + extractExtension(originName);
    }

}

package com.sparta.travelshooting.reviewPost.dto;

public class S3ResponseDto {

    private String imageUrl;

    public S3ResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

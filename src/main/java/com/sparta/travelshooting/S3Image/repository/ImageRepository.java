package com.sparta.travelshooting.S3Image.repository;

import com.sparta.travelshooting.S3Image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
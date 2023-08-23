package com.sparta.travelshooting.admin.service;

import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.comment.repository.CommentRepository;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public AdminSummaryResponseDto showSummary(User user) {
        if (!user.getRole().equals(RoleEnum.ADMIN)) {
            throw new IllegalArgumentException("관리자 권한이 없습니다.");
        }

        return new AdminSummaryResponseDto(userRepository.count(), postRepository.count(), commentRepository.count());
    }
}


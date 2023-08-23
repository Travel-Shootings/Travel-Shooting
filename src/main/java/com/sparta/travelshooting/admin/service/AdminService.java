package com.sparta.travelshooting.admin.service;

import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;


//    //총 유저 수, 총 게시글 수 , 총 댓글 수 조회
//    public AdminSummaryResponseDto showSummary() {
//        return new AdminSummaryResponseDto(userRepository.count(), postRepository.count(), commentRepository.count());
//    }

    // 전체 유저 정보 조회 -> 추후 QueryDsl로 수정 예정
    public List<UserResponseDto> showUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    // 글 전체 조회
    public List<PostResponseDto> showPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 유저 정보 수정
    @Transactional
    public void updateUser(Long userId, AdminProfileRequestDto requestDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NullPointerException("해당 유저는 존재하지 않습니다.");
        }

        /* 프론트에서 공백으로 하고 저장할 경우 공백값이 들어가면 백엔드에서 추가 기능 구현 필요
        1. 수정 원하지 않는 필드 값을 빈칸으로 저장할 경우 어떻게 되는지 확인 필요
        2. region과 role 같은 경우 따로 분리해서 구현할지 or 각 필드 각각 다른 요청으로 구현할지
        */

        user.get().setEmail(requestDto.getEmail());
        user.get().setPassword(requestDto.getPassword());
        user.get().setNickname(requestDto.getNickname());
        user.get().setRegion(requestDto.getRegion());
        user.get().setRole(requestDto.getRole());
        userRepository.save(user.get());
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        }
        post.get().setTitle(requestDto.getTitle());
        post.get().setContents(requestDto.getContents());
        postRepository.save(post.get());
    }


}


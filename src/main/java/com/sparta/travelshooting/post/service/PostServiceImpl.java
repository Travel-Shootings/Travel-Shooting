package com.sparta.travelshooting.post.service;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.entity.JourneyList;
import com.sparta.travelshooting.journeylist.repository.JourneyListRepository;
import com.sparta.travelshooting.post.controller.NaverApiController;
import com.sparta.travelshooting.post.dto.*;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.entity.PostLike;
import com.sparta.travelshooting.post.repository.PostLikeRepository;
import com.sparta.travelshooting.post.repository.PostRepository;
import com.sparta.travelshooting.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final NaverApiController naverApiController;
    private final PostLikeRepository postLikeRepository;
    private final JourneyListRepository journeyListRepository;

    // 게시글 생성
    @Transactional
    @Override
    public PostResponseDto createPostAndJourneyList(PostAndJourneyListDto postAndJourneyListDto, User user) {
        // 게시글과 여행일정 생성
        Post post = new Post(postAndJourneyListDto.getPostRequestDto(), user);
        postRepository.save(post);
        List<JourneyList> postJourneyList = post.getJourneyLists();
        // 여행 일정 생성
        for (JourneyListRequestDto journeyListDto : postAndJourneyListDto.getJourneyListRequestDtos()) {
            JourneyList journeyList = new JourneyList(journeyListDto, post);
            postJourneyList.add(journeyList);
        }
        journeyListRepository.saveAll(postJourneyList);
        return new PostResponseDto(post);
    }


    // 메인 페이지 게시글 3개 조회
    @Override
    public List<PostListResponseDto> getThreePosts() {
        List<Post> postList = postRepository.findTop3ByOrderByCreatedAtDesc();

        return postList.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }


    // 게시글과 여행일정 전체 조회
    @Override
    public List<PostListResponseDto> getPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        return postList.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글과 여행일정 단건 조회
    @Override
    public PostResponseDto getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        return new PostResponseDto(post.get());
    }

    //게시글과 여행 일정 수정
    @Transactional
    @Override
    public PostResponseDto updatePostAndJourneyList(PostAndJourneyListDto postAndJourneyListDto, Long postId, User user) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        Post post = findPost.get();
        post.update(postAndJourneyListDto.getPostRequestDto());

        // 기존 여행 일정 데이터 삭제
        List<JourneyList> journeyList = journeyListRepository.findAllByPostId(postId);
        journeyListRepository.deleteAll(journeyList);

        // 새로운 여행 일정 데이터 생성 및 저장
        List<JourneyList> newJourneyList = new ArrayList<>();
        for (JourneyListRequestDto journeyListDto : postAndJourneyListDto.getJourneyListRequestDtos()) {
            JourneyList journeyListItem = new JourneyList(journeyListDto, post);
            newJourneyList.add(journeyListItem);
        }
        journeyListRepository.saveAll(newJourneyList);

        return new PostResponseDto(postRepository.save(post));
    }

    //게시글과 여행 일정 삭제
    @Transactional
    @Override
    public ApiResponseDto deletePost(Long postId, User user) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        }
        postRepository.deleteById(postId);
        return new ApiResponseDto("게시글 삭제 성공", 200);
    }

    //좋아요 기능
    @Transactional
    @Override
    public ApiResponseDto addLike(Long postId, User user) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<PostLike> findpostLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());
        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        } else if (post.get().getUser().getId().equals(user.getId())) {
            return new ApiResponseDto("자신의 글에는 좋아요를 할 수 없습니다.", 400);
        } else if (findpostLike.isPresent()) {
            return new ApiResponseDto("이미 좋아요를 한 상태입니다.", 400);
        }
        postLikeRepository.save(new PostLike(user, post.get()));
        post.get().setLikeCounts(post.get().getLikeCounts() + 1);
        postRepository.save(post.get());
        return new ApiResponseDto("좋아요 등록 성공", 200);
    }


    @Transactional
    @Override
    public ApiResponseDto deleteLike(Long postId, User user) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<PostLike> findpostLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());
        if (post.isEmpty()) {
            throw new IllegalArgumentException("해당 글은 존재하지 않습니다.");
        } else if (findpostLike.isEmpty()) {
            return new ApiResponseDto("해당 글에 좋아요를 하지 않은 상태입니다.", 400);
        }
        postLikeRepository.delete(findpostLike.get());
        post.get().setLikeCounts(post.get().getLikeCounts() - 1);
        postRepository.save(post.get());
        return new ApiResponseDto("좋아요 취소 성공", 200);
    }
}
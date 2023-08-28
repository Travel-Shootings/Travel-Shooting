package com.sparta.travelshooting.journeylist.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.journeylist.entity.JourneyList;
import com.sparta.travelshooting.journeylist.repository.JourneyListRepository;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JourneyListServiceImpl implements JourneyListService {

    private final JourneyListRepository journeyListRepository;
    private final PostRepository postRepository;

    // 여행 일정 (팝업창) 생성
    @Transactional
    @Override
    public JourneyListResponseDto createJourney(Long postId, JourneyListRequestDto journeyListRequestDto) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        }
        JourneyList journeyList = new JourneyList(journeyListRequestDto, post.get());
        journeyListRepository.save(journeyList);
        return new JourneyListResponseDto(journeyList);
    }

    //여행 일정 수정
    @Transactional
    @Override
    public ApiResponseDto updateJourney(Long postId, Long journeyListId, JourneyListRequestDto journeyListRequestDto) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<JourneyList> journeyList = journeyListRepository.findById(journeyListId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        } else if (journeyList.isEmpty()) {
            throw new NullPointerException("해당 여행 일정은 존재하지 않습니다.");
        }
        journeyList.get().update(journeyListRequestDto);
        return new ApiResponseDto("여행 일정 수정 성공", 200);
    }

    // 여행 일정 삭제
    @Transactional
    @Override
    public ApiResponseDto deleteJourney(Long postId, Long journeyListId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<JourneyList> journeyList = journeyListRepository.findById(journeyListId);
        if (post.isEmpty()) {
            throw new NullPointerException("해당 게시글은 존재하지 않습니다.");
        } else if (journeyList.isEmpty()) {
            throw new NullPointerException("해당 여행 일정은 존재하지 않습니다.");
        }
        journeyListRepository.delete(journeyList.get());
        return new ApiResponseDto("여행 일정 삭제 성공", 200);
    }

}

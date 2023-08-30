package com.sparta.travelshooting.view.controller;

import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.service.ReviewPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    private ReviewPostService reviewPostService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    //로그인 페이지로 이동
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    //회원가입 페이지로 이동
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }



    //후기게시판 전체조회
    @GetMapping("/reviewPost")
    public String viewAllReviewPost(){
        return "reviewPost";
    }

    //후기게시판 단건조회
    @GetMapping("/reviewPost/{reviewPostId}")
    public String viewReviewPost(@PathVariable Long reviewPostId, Model model){
        try {
            ReviewPostResponseDto responseDto = reviewPostService.getReviewPost(reviewPostId);
            model.addAttribute("reviewPost", responseDto);
            return "viewReview"; // viewReview는 뷰 페이지의 이름을 나타냅니다.
        } catch (IllegalArgumentException e) {
            // 게시물을 찾지 못한 경우에 대한 예외 처리 로직을 추가할 수 있습니다.
            return "errorPage"; // 예시: 오류 페이지로 리다이렉트
        }
    }

    @GetMapping("/reviewPost/create")
    public String createReview() {
        return "createReview";
    }


}

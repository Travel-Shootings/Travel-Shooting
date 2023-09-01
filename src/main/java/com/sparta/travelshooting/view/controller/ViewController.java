package com.sparta.travelshooting.view.controller;

import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.service.ReviewPostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
@AllArgsConstructor
public class ViewController {

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

    //게시글 작성 페이지로 이동
    @GetMapping("/create-post")
    public String createPost () {
        return "createPost";
    }

    //게시글 단건 조회 페이지로 이동
    @GetMapping("/show-post")
    public String showPost () {
        return "showPost";
    }

    //후기게시판 전체조회
    @GetMapping("/reviewPost")
    public String viewAllReviewPost(){
        return "reviewPost";
    }

    //후기게시판 단건조회
    @GetMapping("/reviewPost/{reviewPostId}")
    public String viewReviewPost(){
            return "viewReview";
    }

    //후기게시판 생성
    @GetMapping("/reviewPost/create")
    public String createReview() {
        return "createReview";
    }

    //후기게시판 수정

    @GetMapping("/reviewPost/update/{reviewPostId}")
    public String updateReview(){
        return "updateReview";
    }




}

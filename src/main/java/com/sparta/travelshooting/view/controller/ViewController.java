package com.sparta.travelshooting.view.controller;

import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/view")
@AllArgsConstructor
public class ViewController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("nickname", userDetails.getUser().getNickname());
            model.addAttribute("role", String.valueOf(userDetails.getUser().getRole()));
        }
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

    //유저 프로필로 이동
    @GetMapping("/user/profile")
    public String userProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("nickname", user.getNickname());
            model.addAttribute("region", String.valueOf(user.getRegion()));
            model.addAttribute("role", String.valueOf(user.getRole()));
        }
        return "profile";
    }

    //유저 프로필 수정 페이지
    @GetMapping("/user/edit-profile")
    public String editProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickname", user.getNickname());
            model.addAttribute("region", String.valueOf(user.getRegion()));
        }
        return "edit_profile";
    }

    //비밀번호 변경 수정 페이지
    @GetMapping("/user/edit-password")
    public String editPassword() {
        return "edit_password";
    }


    //admin 페이지
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/user-edit")
    public String adminUserEditProfile(@RequestParam Long userId, Model model) {
        model.addAttribute("userId", userId);

        return "admin_user_edit_profile";
    }

    //게시글 작성 페이지로 이동
    @GetMapping("/post/create")
    public String createPost () {
        return "create_post";
    }

    //게시글 단건 조회 페이지로 이동
    @GetMapping("/post/view")
    public String showPost () {
        return "view_post";
    }

    //후기게시판 전체조회
    @GetMapping("/review-post")
    public String viewAllReviewPost(){
        return "review_post";
    }

    //후기게시판 단건조회
    @GetMapping("/review-post/{reviewPostId}")
    public String viewReviewPost(){
            return "view_review";
    }

    //후기게시판 생성
    @GetMapping("/review-post/create")
    public String createReview() {
        return "create_review";
    }

    //후기게시판 수정
    @GetMapping("/review-post/update/{reviewPostId}")
    public String updateReview(){
        return "update_review";
    }



}

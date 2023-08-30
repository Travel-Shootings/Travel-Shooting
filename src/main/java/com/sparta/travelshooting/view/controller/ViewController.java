package com.sparta.travelshooting.view.controller;

import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("nickname", userDetails.getUser().getNickname());
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
    @GetMapping("/user/editProfile")
    public String editProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickname", user.getNickname());
            model.addAttribute("region", String.valueOf(user.getRegion()));
        }
        return "editProfile";
    }

    //비밀번호 변경 수정 페이지
    @GetMapping("/user/editPassword")
    public String editPassword() {
        return "editPassword";
    }
}

package com.sparta.travelshooting.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
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
}

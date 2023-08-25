package com.sparta.travelshooting.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //로그인 페이지로 이동
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }
}

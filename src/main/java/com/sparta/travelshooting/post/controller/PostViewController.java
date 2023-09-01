package com.sparta.travelshooting.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {

    @GetMapping("/create-post")
    public String createPost () {
        return "create-post";
    }

    @GetMapping("/show-post")
    public String showPost () {
        return "show-post";
    }
}

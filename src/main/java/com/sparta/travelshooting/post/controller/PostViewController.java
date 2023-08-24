package com.sparta.travelshooting.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {

    @GetMapping("/test")
    public String test () {
        return "test";
    }

    @GetMapping("/test/board")
    public String testBoard () {
        return "board";
    }
}

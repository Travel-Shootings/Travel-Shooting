package com.sparta.travelshooting.view.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

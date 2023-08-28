package com.sparta.travelshooting.journeylist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JourneyListViewController {

    @GetMapping("/journey")
    public String journey() {
        return "journeyList";
    }
}

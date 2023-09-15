package com.sparta.travelshooting.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DomainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/view/home";
    }
}

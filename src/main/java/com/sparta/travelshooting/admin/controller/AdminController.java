package com.sparta.travelshooting.admin.controller;

import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.admin.service.AdminService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/summary")
    public AdminSummaryResponseDto showSummary (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.showSummary(userDetails.getUser());
    }
}

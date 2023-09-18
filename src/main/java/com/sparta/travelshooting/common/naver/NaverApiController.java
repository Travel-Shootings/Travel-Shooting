package com.sparta.travelshooting.common.naver;

import com.sparta.travelshooting.common.service.NaverApiService;
import com.sparta.travelshooting.post.dto.NaverAddressDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/server")
@Tag(name = "네이버 API")
public class NaverApiController {

    private final NaverApiService naverApiService;

    @Operation(summary = "네이버 검색 API")
    @GetMapping("/naver")
    public ResponseEntity<List<NaverAddressDto>> searchAddress(@RequestParam("query") String query, Model model) {
        List<NaverAddressDto> naverAddressDtoList = naverApiService.searchAddress(query);
        model.addAttribute("naverSearch", naverAddressDtoList);
        return new ResponseEntity<>(naverAddressDtoList, HttpStatus.OK);
    }
    
}

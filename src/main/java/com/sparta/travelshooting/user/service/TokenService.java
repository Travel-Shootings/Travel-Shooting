package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

     /**
      * 토큰 재발급 API
      * @param user 토큰 재발급 요청 유저 정보
      * @param res
      */
     TokenResponseDto requestRefreshToken(User user, HttpServletResponse res);
}

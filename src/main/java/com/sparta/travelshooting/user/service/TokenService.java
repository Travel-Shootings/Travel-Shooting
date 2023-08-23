package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

     /**
      * 토큰 재발급 API
      * @param res
      * @param req
      */
     TokenResponseDto requestRefreshToken(HttpServletResponse res, HttpServletRequest req);
}

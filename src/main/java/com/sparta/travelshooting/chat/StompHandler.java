package com.sparta.travelshooting.chat;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (headerAccessor.getCommand() == StompCommand.CONNECT) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) headerAccessor.getHeader("simpUser");
            UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
            User user = userDetails.getUser();

            // 세션이 유지되는 동안 재사용할 정보: 닉네임과 ID
            headerAccessor.getSessionAttributes().put("user", user);
        }

        return message;
    }
}

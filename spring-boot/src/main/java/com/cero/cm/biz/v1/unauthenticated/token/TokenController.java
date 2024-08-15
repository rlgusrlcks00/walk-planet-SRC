package com.cero.cm.biz.v1.unauthenticated.token;

import com.cero.cm.config.config.JwtResponse;
import com.cero.cm.config.config.JwtTokenUtil;
import com.cero.cm.config.exception.UnauthorizedException;
import com.cero.cm.db.repository.user.UserRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import com.cero.cm.db.entity.User;

@RestController
@RequestMapping(path = "/open/token")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"토큰 갱신"})
public class TokenController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Operation(
            summary = "토큰 조회",
            description = "토큰을 조회합니다."
    )
    @GetMapping(value = "/token")
    public String getToken(@RequestParam("token") String token) {
        return userRepository.findByToken(token);
    }

    @Operation(
            summary = "토큰 갱신",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다."
    )
    @PostMapping(value = "/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            User user = userRepository.findByUserEmail(username);

            if (user == null || !jwtTokenUtil.validateToken(refreshToken)) {
                throw new UnauthorizedException("유효하지 않은 리프레시 토큰입니다.");
            }

            String newAccessToken = jwtTokenUtil.generateToken(user);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);

            return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(null, null));
        }
    }
}

package com.cero.cm.biz.v1.unauthenticated.login.service;

import com.cero.cm.biz.v1.unauthenticated.login.model.req.LoginReq;
import com.cero.cm.biz.v1.unauthenticated.login.model.res.LoginRes;
import com.cero.cm.config.config.JwtTokenUtil;
import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.stephistory.StepHistoryRepository;
import com.cero.cm.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final StepHistoryRepository stepHistoryRepository;

    public LoginRes loginUser(LoginReq req) {
        LocalDateTime today = LocalDate.now().atStartOfDay();

        User user = userRepository.findByUserEmail(req.getUserEmail());

        if(Objects.equals(user.getDelYn(), "Y")) {
            throw new IllegalArgumentException("Deleted user");
        }

        Long todayStepCount = stepHistoryRepository.findStepCountByUserIdAndRegDt(user.getUserId(), today);

        if(todayStepCount == null) {
            todayStepCount = 0L;
        }

        if(user == null || !passwordEncoder.matches(req.getUserPwd(), user.getUserPwd())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        LoginRes res = LoginRes.builder()
                .userId(user.getUserId())
                .userName(user.getUserRealName())
                .userEmail(user.getUserEmail())
                .stepCount(todayStepCount)
                .isFirstTimeSetupDone(user.getIsFirstTimeSetupDone())
                .build();

        res.setToken(jwtTokenUtil.generateToken(user));
        res.setRefreshToken(jwtTokenUtil.generateRefreshToken(user)); // 리프레시 토큰 설정

        user.setToken(res.getToken());
        user.setRefreshToken(res.getRefreshToken());

        userRepository.save(user); // 토큰 저장 (DB)

        return res;
    }
}

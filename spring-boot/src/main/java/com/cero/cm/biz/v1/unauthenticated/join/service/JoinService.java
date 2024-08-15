package com.cero.cm.biz.v1.unauthenticated.join.service;

import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepGoalsReq;
import com.cero.cm.biz.v1.unauthenticated.join.model.req.JoinReq;
import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.stepgoals.StepGoalsRepository;
import com.cero.cm.db.repository.steptotal.StepTotalRepository;
import com.cero.cm.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StepTotalRepository stepTotalRepository;
    private final StepGoalsRepository stepGoalsRepository;

    @Transactional
    public String joinUser(JoinReq req) {

        boolean existsReq = req == null;
        if(existsReq) {
            throw new IllegalArgumentException("Request is null");
        }

        boolean isSameEmail = userRepository.existsByUserEmail(req.getUserEmail());
        if(isSameEmail) {
            return "Email already exists";
        }

        // 비밀번호 암호화
        req.setUserPwd(passwordEncoder.encode(req.getUserPwd()));
        // User 정보 저장
        User user = userRepository.save(req.toUserEntity());
        Long userId = user.getUserId();

        // StepTotal 정보 저장(회원가입 시 최초 1회 등록)
        stepTotalRepository.save(req.toStepTotalEntity(user));

        // StepGoals 정보 저장(회원가입 시 최초 1회 등록)
        stepGoalsRepository.save(SaveStepGoalsReq.builder().stepGoalsCount(10000L).build().toStepGoalsEntity(userId));

        return "success";
    }
}

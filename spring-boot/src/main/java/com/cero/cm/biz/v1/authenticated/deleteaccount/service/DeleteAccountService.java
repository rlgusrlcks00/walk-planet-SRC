package com.cero.cm.biz.v1.authenticated.deleteaccount.service;

import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.user.UserRepository;
import com.cero.cm.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAccountService {
    private final UserRepository userRepository;

    @Transactional
    public String deleteAccount() {
        Long userId = TokenUtil.getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setDelYn("Y");
        userRepository.save(user);
        return "success";
    }
}

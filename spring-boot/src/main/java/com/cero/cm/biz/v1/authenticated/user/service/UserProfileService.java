package com.cero.cm.biz.v1.authenticated.user.service;

import com.cero.cm.biz.v1.authenticated.user.model.req.SaveUserProfileReq;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.service.FileStorageService;
import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.user.UserRepository;
import com.cero.cm.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public User saveUserProfile(SaveUserProfileReq saveUserProfileReq) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String profileImgUrl = user.getProfileImg();
        FileStorageService fileStorageService = new FileStorageService();
        if(saveUserProfileReq.getProfileImg() != null) {
            profileImgUrl = fileStorageService.storeProfileFile(saveUserProfileReq.getProfileImg());
            user.setProfileImg(profileImgUrl);
        }

        logger.info("Profile image uploaded to: " + profileImgUrl);

        user.setBirth(saveUserProfileReq.getBirth());
        user.setGender(saveUserProfileReq.getGender());
        user.setNickname(saveUserProfileReq.getNickname());
        user.setHeight(saveUserProfileReq.getHeight());
        user.setWeight(saveUserProfileReq.getWeight());
        user.setWeightGoals(saveUserProfileReq.getWeightGoals());
        user.setIsFirstTimeSetupDone(saveUserProfileReq.getIsFirstTimeSetupDone());
        user.setModDt(now);

        return userRepository.save(user);
    }

    public User getUserProfile() {
        Long userId = TokenUtil.getUserId();
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

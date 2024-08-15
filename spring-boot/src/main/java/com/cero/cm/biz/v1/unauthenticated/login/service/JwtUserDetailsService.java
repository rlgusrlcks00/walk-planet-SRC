package com.cero.cm.biz.v1.unauthenticated.login.service;

import com.cero.cm.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import com.cero.cm.db.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        User user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(("User not find with eamil: " + email));
        }
        return userRepository.findByUserEmail(email);
    }

}

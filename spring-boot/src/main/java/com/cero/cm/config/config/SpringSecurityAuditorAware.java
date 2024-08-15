package com.cero.cm.config.config;

import com.cero.cm.db.entity.User;
import com.cero.cm.util.TokenUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//@EnableJpaAuditing
@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		/**
		 * SecurityContext 에서 인증정보를 가져와 주입시킨다.
		 * 현재 코드는 현재 Context 유저가 USER 인가 권한이 있으면, 해당 Principal name 을 대입하고, 아니면 Null 을 set 한다.
		 */

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = TokenUtil.getAdminInfo();
			if (user == null)
				return Optional.of(1L).map(aLong -> {
				return 1L;
			});
			return Optional.of(user.getUserId()).map(aLong -> {
				return aLong;
			});
		}
		return null;
	}

//		return Optional.of(SecurityContextHolder.getContext())
//				.map(SecurityContext::getAuthentication)
//				.map(authentication -> {
//					HooxiUser hooxiUser = ( HooxiUser ) authentication.getPrincipal();
//					if(hooxiUser == null || StringUtils.isBlank(hooxiUser.getMmbrId())) return 1L;
//					return Long.valueOf(hooxiUser.getMmbrId());
//				});
}

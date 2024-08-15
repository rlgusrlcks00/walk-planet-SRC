package com.cero.cm.util;

import com.cero.cm.db.entity.User;
import com.cero.cm.exception.ErrorCode;
import com.cero.cm.exception.CommonCodeException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenUtil {

    public static User getAdminInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal().equals("anonymousUser")) throw new CommonCodeException(ErrorCode.TOKEN_FORBIDDEN);

        return (User) auth.getPrincipal();
    }

    public static String getAdminEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal().equals("anonymousUser")) throw new CommonCodeException(ErrorCode.TOKEN_FORBIDDEN);

        User admin = (User) auth.getPrincipal();
        return admin.getUserEmail();
    }

    public static Long getUserId() {
        return getAdminInfo().getUserId();
    }
}

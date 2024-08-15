package com.cero.cm.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
    public static MessageSourceAccessor messageSourceAccessor;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public static String getMessage(String code) {
        return MessageUtils.messageSourceAccessor.getMessage(code);
    }

    public static String getMessage(String code, Object[] args) {
        return MessageUtils.messageSourceAccessor.getMessage(code, args);
    }

    public static String getMessage(String code, String defaultMessage) {
        return MessageUtils.messageSourceAccessor.getMessage(code, defaultMessage);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return MessageUtils.messageSourceAccessor.getMessage(code, args, defaultMessage);
    }

    public static boolean isMessageCodeExists(String code) {
        try {
            MessageUtils.messageSourceAccessor.getMessage(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

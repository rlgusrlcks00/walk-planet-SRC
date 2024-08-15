package com.cero.cm.config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    /**
     * Spring Message 설정 부분.
     */
    @Bean
    public LocaleResolver defaultLocaleResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREAN);

        return localeResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        Locale.setDefault(Locale.KOREAN); // 제공하지 않는 언어로 들어왔을 때 처리

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/message/code");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.getDefault());
        messageSource.setCacheMillis(3);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true); // 요청받은 Locale에 대한 파일을 찾지 못할 경우 시스템 설정 Locale을 사용할 것인지에 대한 유무

        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor (@Autowired ReloadableResourceBundleMessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }

    /*@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/message/code/code", "classpath:/message/code");
        messageSource.setDefaultEncoding("UTF-8");
//		messageSource.setCacheMillis(-1);
        messageSource.setCacheMillis(3);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource());
        return messageSourceAccessor;
    }*/

}

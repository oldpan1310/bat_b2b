package com.bat.distributor.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化工具类
 */
@Component
@Slf4j
public class MessageUtils {

    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String get(String msgKey) {
        LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
        if (localeContext == null) {
            log.info("没有默认语言 设置为中文");
            LocaleContextHolder.setLocale(Locale.CHINA, true);
        }
        return get(msgKey, LocaleContextHolder.getLocale());
    }

    public static String get(String msgKey, Locale locale) {
        try {
            return messageSource.getMessage(msgKey, null, locale);
        } catch (Exception e) {
            return msgKey;
        }
    }
}
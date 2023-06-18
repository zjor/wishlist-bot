package com.github.zjor.ext.spring.auth;

import com.github.zjor.controller.ControllerUtils;
import com.github.zjor.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.github.zjor.controller.ControllerUtils.notFoundSupplier;
import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    public AuthUserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthUser.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest req,
            WebDataBinderFactory binderFactory) throws Exception {
        var telegramId = req.getHeader(X_TELEGRAM_USER);
        if (StringUtils.isEmpty(telegramId)) {
            throw ControllerUtils.unauthorized(X_TELEGRAM_USER + " is " + telegramId);
        }
        return userRepository.findUserByExtId(telegramId).orElseThrow(notFoundSupplier(telegramId));
    }
}

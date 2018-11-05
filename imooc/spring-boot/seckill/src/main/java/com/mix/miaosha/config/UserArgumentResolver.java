package com.mix.miaosha.config;

import com.mix.miaosha.domain.entity.MiaoshaUser;
import com.mix.miaosha.domain.result.CodeMsg;
import com.mix.miaosha.domain.vo.TokenUserVo;
import com.mix.miaosha.exception.GlobalException;
import com.mix.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == TokenUserVo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            throw new GlobalException(CodeMsg.TOKEN_USER_NOT_EXIST);
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response, token);
        TokenUserVo tokenUserVo = new TokenUserVo();
        tokenUserVo.setMobile(miaoshaUser.getMobile());
        tokenUserVo.setName(miaoshaUser.getName());
        tokenUserVo.setPassword(miaoshaUser.getPassword());
        tokenUserVo.setSalt(miaoshaUser.getSalt());
        tokenUserVo.setId(miaoshaUser.getId());
        // System.out.println("触发UserArgumentResolver");
        return tokenUserVo;
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieNameToken)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
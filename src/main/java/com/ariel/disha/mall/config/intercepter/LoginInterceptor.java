package com.ariel.disha.mall.config.intercepter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.config.redis.RedisService;
import com.ariel.disha.mall.consts.HttpConst;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.TokenVo;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author ariel
 * @apiNote LoginInterceptor
 * @serial
 */
public class LoginInterceptor implements HandlerInterceptor {

    private AppProperties appProperties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(authorization)) {
            errorResponse(response, HttpStatus.AUTH_HEADER_IS_NULL_ERROR);
            return false;
        }
        String token = authorization.substring(HttpConst.BEARER.length());
        if (StrUtil.isBlank(token)) {
            errorResponse(response, HttpStatus.AUTH_HEADER_IS_NULL_ERROR);
            return false;
        }
        TokenVo tokenVo = JwtUtil.decodeToken(token, appProperties.getSecret());
        if (tokenVo == null) {
            errorResponse(response, HttpStatus.AUTH_ERROR);
            return false;
        }

        String userId = request.getHeader(HttpConst.USER_ID_HEADER);
        if (StrUtil.isBlank(userId)) {
            errorResponse(response, HttpStatus.AUTH_ERROR);
            return false;
        }
        LoginDto dto = RedisService.USER_INFO.getData(userId);
        if (Objects.isNull(dto)) {
            errorResponse(response, HttpStatus.DATA_NOT_EXISTS);
            return false;
        }
//        if (!UserState.NORMAL.equals(dto.getState())) {
//            errorResponse(response, HttpStatus.USER_IS_DISABLED);
//            return false;
//        }
        return true;
    }

    private void errorResponse(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSONUtil.toJsonStr(HttpResponse.error(httpStatus)));
    }


}

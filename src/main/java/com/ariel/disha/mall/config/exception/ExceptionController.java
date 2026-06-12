package com.ariel.disha.mall.config.exception;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.SymbolConst;
import com.ariel.disha.mall.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

/**
 * @author ariel
 * @apiNote ExceptionController
 * @serial
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AppException.class)
    public HttpResponse<Void> handleAppException(AppException e) {
        log.error("AppException[{}]", e.getMsg());
        return HttpResponse.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(AssertUtil.AssertException.class)
    public HttpResponse<Void> handleAssertException(RuntimeException e) {
        log.error("AssertException[{}]", e.getMessage());
        return HttpResponse.error(HttpStatus.PARAM_ERROR.getStatus(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public HttpResponse<Void> handleBindException(BindException e) {
        StringJoiner joiner = new StringJoiner(SymbolConst.BLANK);
        e.getBindingResult().getFieldErrors().forEach(fe -> {
            joiner.add(fe.getField() + fe.getDefaultMessage() + ".");
        });
        String msg = joiner.toString();
        log.error("BindException[{}]", msg);
        return HttpResponse.error(HttpStatus.PARAM_ERROR.getStatus(), msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public HttpResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException[{}]", e.getMessage());
        return HttpResponse.error(HttpStatus.PARAM_ERROR.getStatus(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HttpResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException[{}]", e.getMessage());
        return HttpResponse.error(HttpStatus.PARAM_ERROR.getStatus(), e.getMessage());
    }
}

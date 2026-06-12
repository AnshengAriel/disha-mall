package com.ariel.disha.mall.config.exception;

import com.ariel.disha.mall.consts.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author ariel
 * @apiNote AppException
 * @serial
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AppException extends RuntimeException {

    private final Integer code;
    private final String msg;

    public AppException(HttpStatus httpStatus) {
        this(httpStatus.getStatus(), httpStatus.getMsg());
    }

    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public AppException(String message) {
        this(HttpStatus.CONNECT_ERROR.getStatus(), message);
    }

}

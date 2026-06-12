package com.ariel.disha.mall.consts;

import com.ariel.disha.mall.config.exception.AppException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ariel
 * @apiNote HttpResponse
 * @serial
 */
@Data
@Builder
public class HttpResponse<T> implements Serializable {

    private int status;

    private String msg;

    private T data;

    public HttpResponse() {

    }

    public HttpResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpResponse(int status, String msg) {
        this(status, msg, null);
    }

    public HttpResponse(HttpStatus httpStatus) {
        this(httpStatus.getStatus(), httpStatus.getMsg());
    }

    public HttpResponse(HttpStatus httpStatus, T data) {
        this(httpStatus.getStatus(), httpStatus.getMsg(), data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return getStatus() == HttpStatus.SUCCESS.getStatus();
    }

    public static <T> HttpResponse<T> success() {
        return new HttpResponse<>(HttpStatus.SUCCESS, null);
    }

    public static <T> HttpResponse<T> success(T data) {
        return new HttpResponse<>(HttpStatus.SUCCESS, data);
    }
    
    public static <T> HttpResponse<T> error(HttpStatus httpStatus) {
         return new HttpResponse<>(httpStatus);
    }

    public static <T> HttpResponse<T> error(Integer status, String msg) {
        return new HttpResponse<>(status, msg);
    }

    public static <T> HttpResponse<T> presentOrNot(T data) {
        return data != null ? HttpResponse.success(data) : HttpResponse.error(HttpStatus.DATA_NOT_EXISTS);
    }

    public static <T> HttpResponse<T> updatedOrNot(int updated) {
        return updated < 1 ? HttpResponse.error(HttpStatus.DATA_UPDATED_FAILED) : HttpResponse.success();
    }

    public static <T> HttpResponse<T> insertedOrNot(int inserted) {
        return inserted == 0 ? HttpResponse.error(HttpStatus.DATA_INSERTED_FAILED) : HttpResponse.success();
    }

    public static <T> HttpResponse<T> deletedOrNot(int deleted) {
        return deleted == 0 ? HttpResponse.error(HttpStatus.DATA_DELETE_FAILED) : HttpResponse.success();
    }

    public T orElseThrow() {
        if (!isSuccess()) {
            throw new AppException(getStatus(), getMsg());
        }
        return getData();
    }
}

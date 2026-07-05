
package com.ariel.disha.mall.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author ariel
 * @apiNote HttpStatus
 * @serial
 */
@Getter
@AllArgsConstructor
public enum HttpStatus {
    // 网络
    SUCCESS(10200, "success"),
    CONNECT_ERROR(10201, "network error"),
    PARAM_ERROR(10202, "The parameter is error"),
    AUTH_ERROR(10203, "Auth error"),
    URL_ERROR(10204, "Request url is not exist"),
    AUTH_HEADER_IS_NULL_ERROR(10205, "Auth header is null"),

    // CRUD
    DATA_INSERTED_FAILED(10300, "Insert data failed"),
    DATA_DELETE_FAILED(10301, "The data delete failed"),
    DATA_NOT_EXISTS(10302, "The data does not exist"),
    DATA_UPDATED_FAILED(10304, "The data updated failed"),
    DATA_UNIQUE_ID_EXISTS(10305, "The unique id exists"),

    // 用户
    CHECK_CODE_GENERATE_ERROR(20000, "Generate check code failed"),
    CHECK_CODE_VALIDATE_FAILED(20001, "Validate check code failed"),
    LONGIN_TIME_OUT(20002, "The login has timed out, please log in again"),
    ACCOUNT_LOGIN_FAILED(20003, "The account or password is wrong, please log in again"),
    EMAIL_IS_EXISTS(20004, "The email is exists"),
    NICK_NAME_IS_EXISTS(20005, "The nick name is exists"),
    USER_NOT_EXISTS_OR_PASSWORD_WRONG(20006, "The user is not exists or password is wrong"),
    USER_IS_DISABLED(20007, "The account is disabled"),
    USER_NICKNAME_IS_EXISTS(20008, "The nickname of user is exists"),
    USER_CAN_NOT_FOCUS_MYSELF(20009, "User can't focus self"),

    // 文件
    FILE_UPLOAD_FAILED(20100, "The file upload failed"),
    FILE_IS_EXISTS(20101, "The file is exists"),

    // 分类
    CATEGORY_CODE_EXISTS(20300, "The category code exists"),

    // 投稿
    SUBMISSION_VIDEO_NOT_EXIST(20400, "The video dose not exist"),
    SUBMISSION_DISABLED_BULLET(20401, "The video is forbidden to publish bullet screen"),

    // 投稿系列
    SUBMISSION_OF_SERIES_NOT_EXIST(20500, "The video of series dose not exist"),
    ;

    private final int status;

    private final String msg;
}

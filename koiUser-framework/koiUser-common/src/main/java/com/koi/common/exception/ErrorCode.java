package com.koi.common.exception;

import lombok.Data;

/**
 * 错误码对象
 *
 * @Author zjl
 * @Date 2023/7/29 17:04
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}

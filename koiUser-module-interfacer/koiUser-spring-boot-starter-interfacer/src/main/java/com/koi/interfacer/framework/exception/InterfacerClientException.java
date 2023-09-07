package com.koi.interfacer.framework.exception;

/**
 * InterfacerClientException
 *
 * @Author zjl
 * @Date 2023/9/7 10:32
 */
public class InterfacerClientException extends RuntimeException {

    public InterfacerClientException(String message) {
        super(message);
    }

    public InterfacerClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

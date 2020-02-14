package com.atguigu.atcrowdfunding.exception;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-04 15:41
 * @Description:
 */
public class LoginFailException extends RuntimeException {
    public LoginFailException(String message) {
        super(message);
    }
}

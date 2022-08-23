package com.example.demo.api;

import lombok.Data;

/**
 * @author codeeasily
 * @date 2022/08/16 21:01
 */
@Data
public class APIResult<T> {
    private Integer code = 1;
    private String message = "成功";
    private T data;

    private APIResult(T data) {
        this.data = data;
    }

    private APIResult() {

    }


    private APIResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> APIResult<T> success() {
        return new APIResult<>();
    }

    public static <T> APIResult<T> success(T data) {
        return new APIResult<>(data);
    }

    private static <T> APIResult<T> fail(T data) {
        return new APIResult<>(-1, "失败", data);
    }

    private static <T> APIResult<T> fail() {
        return new APIResult<>(-1, "失败", null);
    }

    public static <T> APIResult<T> fail(Integer code, String message, T data) {
        return new APIResult<>(code, message, data);
    }
}

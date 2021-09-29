package com.example.demo.controller.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiResult<T> {
    private final T data;
    private final String error;

    public ApiResult(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> succeed(T data) {
        return new ApiResult<>(data, null);
    }

    public static ApiResult<?> failed(Throwable throwable) {
        return failed(throwable.getMessage());
    }

    public static ApiResult<?> failed(String message) {
        return new ApiResult<>(null, message);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}

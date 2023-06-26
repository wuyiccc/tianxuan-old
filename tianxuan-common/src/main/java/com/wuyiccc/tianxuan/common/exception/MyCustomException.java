package com.wuyiccc.tianxuan.common.exception;

import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:54
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为: " + responseStatusEnum.status() + "异常信息为: " + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}

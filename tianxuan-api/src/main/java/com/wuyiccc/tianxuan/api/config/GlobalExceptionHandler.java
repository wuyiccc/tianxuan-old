package com.wuyiccc.tianxuan.api.config;

import com.wuyiccc.tianxuan.common.exception.MyCustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:59
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public CommonResult<String> returnMyCustomException(MyCustomException e) {
        return CommonResult.exception(e.getResponseStatusEnum());
    }



}

package com.wuyiccc.tianxuan.api.config;

import com.wuyiccc.tianxuan.common.exception.MyCustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult<Map<String, String>> returnNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        return CommonResult.errorData(errors);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 错误字段名称
            String fieldName = error.getField();
            // 错误信息
            String errMsg = error.getDefaultMessage();
            errorMap.put(fieldName, errMsg);
        }
        return errorMap;
    }


}

package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.common.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2023/6/24 22:39
 */
@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {

    @Autowired
    private SmsUtils smsUtils;

    @GetMapping("/getSMSCode")
    public CommonResult<String> getSMSCode(String mobile, HttpServletRequest request) {

        if (StringUtils.isBlank(mobile)) {
            return CommonResult.errorMsg("手机号不可为空");
        }

        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("当前验证码为: {}", code);
//            smsUtils.sendSMS(mobile, code);
        // 设置验证码过期时间为30min
        redisUtils.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);
        return CommonResult.ok("发送短信成功");
    }
}

package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.util.IPUtil;
import com.wuyiccc.tianxuan.common.util.SmsUtils;
import com.wuyiccc.tianxuan.pojo.bo.RegisterLoginBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

        // 限制用户只能在60s以内获得一次验证码
        String requestIp = IPUtil.getRequestIp(request);
        redisUtils.setnx60s(MOBILE_SMSCODE + ":" + requestIp, mobile);

        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("当前验证码为: {}", code);
        // TODO(wuyiccc): 发送短信验证码
//            smsUtils.sendSMS(mobile, code);
        // 设置验证码过期时间为30min
        redisUtils.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);
        return CommonResult.ok("发送短信成功");
    }

    @PostMapping("/login")
    public CommonResult<String> login(@Valid @RequestBody RegisterLoginBO registerLoginBO
    , HttpServletRequest request) {
        String mobile = registerLoginBO.getMobile();
        String smsCode = registerLoginBO.getSmsCode();
        return CommonResult.ok();
    }
}
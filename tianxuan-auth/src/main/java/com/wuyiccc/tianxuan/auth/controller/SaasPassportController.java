package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2023/7/22 07:58
 */
@RestController
@RequestMapping("/saas")
@Slf4j
public class SaasPassportController extends BaseInfoProperties {

    @PostMapping("/getQRToken")
    public CommonResult<String> getQRToken() {

        // 生成扫码登录的token
        String qrToken = UUID.randomUUID().toString();
        // 把qrtoken存入到redis, 设置一定时效, 默认二维码超时, 则需要刷新后再次获得新的二维码
        redisUtils.set(SAAS_PLATFORM_LOGIN_TOKEN + ":" + qrToken, qrToken, 5 * 60);
        // 存入redis标记当前的qrtoken未被扫描读取 0代表未被读取 1代表已被读取
        redisUtils.set(SAAS_PLATFORM_LOGIN_TOKEN_READ + ":" + qrToken, "0", 5 * 60);

        // 返回给前端, 让前端下一次请求的时候需要带上qrtoken
        return CommonResult.ok(qrToken);
    }


}

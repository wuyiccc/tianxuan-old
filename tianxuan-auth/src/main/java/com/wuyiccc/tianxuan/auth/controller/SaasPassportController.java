package com.wuyiccc.tianxuan.auth.controller;

import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2023/7/22 07:58
 */
@RestController
@RequestMapping("/saas")
@Slf4j
public class SaasPassportController extends BaseInfoProperties {


    @Autowired
    private JWTUtils jwtUtils;

    /**
     * 获得二维码token令牌
     */
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


    @PostMapping("/scanCode")
    public CommonResult<String> scanCode(String qrToken, HttpServletRequest request) {

        if (StringUtils.isBlank(qrToken)) {
            throw new CustomException(ResponseStatusEnum.FAILED);
        }

        String redisQRToken = redisUtils.get(SAAS_PLATFORM_LOGIN_TOKEN + ":" + qrToken);
        if (!Objects.equals(redisQRToken, qrToken)) {
            return CommonResult.errorCustom(ResponseStatusEnum.FAILED);
        }

        // TODO(wuyiccc): 后续变更为headerUserToken, uniapp对应接口一起变更
        String headerUserId = request.getHeader("appUserId");
        String headerUserToken = request.getHeader("appUserToken");

        if (StringUtils.isBlank(headerUserToken) || StringUtils.isBlank(headerUserId)) {
            return CommonResult.errorCustom(ResponseStatusEnum.HR_TICKET_INVALID);
        }

        String userJson = jwtUtils.checkJWT(headerUserToken.split("@")[1]);
        if (StringUtils.isBlank(userJson)) {
            return CommonResult.errorCustom(ResponseStatusEnum.HR_TICKET_INVALID);
        }

        // 生成预登录token令牌
        String preToken = UUID.randomUUID().toString();
        redisUtils.set(SAAS_PLATFORM_LOGIN_TOKEN + ":" + qrToken, preToken, 5 * 60);

        // 1代表已经被扫描, 标记qrtoken失效
        redisUtils.set(SAAS_PLATFORM_LOGIN_TOKEN_READ + ":" + qrToken, "1," + preToken, 5 * 60);

        // 返回给手机端, app下次请求携带preToken
        return CommonResult.ok(preToken);
    }

    /**
     * saas网页端每隔一段时间(3s)定时查询qrToken是否被读取
     */
    @PostMapping("/codeHasBeenRead")
    public CommonResult<List<String>> codeHasBeenRead(String qrToken) {

        String readStr = redisUtils.get(SAAS_PLATFORM_LOGIN_TOKEN_READ + ":" + qrToken);

        if (StringUtils.isBlank(readStr)) {
            log.error("readStr is null");
            throw new CustomException(ResponseStatusEnum.FAILED);
        }

        String[] readArr = readStr.split(",");
        List<String> list = new ArrayList<>();
        if (readArr.length >= 2) {
            list.add(readArr[0]);
            list.add(readArr[1]);
        }
        return CommonResult.ok(list);
    }



}
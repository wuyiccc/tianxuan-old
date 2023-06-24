package com.wuyiccc.tianxuan.gateway.controller;

import com.wuyiccc.tianxuan.common.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private SmsUtils smsUtils;
    @GetMapping("/hello")
    public Object hello() throws Exception {
        smsUtils.sendSMS("15972722916", "10000");
        return "hello gateway";
    }
}

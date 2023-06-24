package com.wuyiccc.tianxuan.user.controller;

import com.wuyiccc.tianxuan.common.CommonResult;
import com.wuyiccc.tianxuan.pojo.test.Stu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public CommonResult<Stu> hello() {

        Stu stu = new Stu("10001", "zhangsan", 29);

        log.debug(stu.toString());
        return CommonResult.ok(stu);
    }
}

package com.wuyiccc.hire.user.controller;

import com.wuyiccc.hire.common.pojo.Stu;
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
    public Object hello() {

        Stu stu = new Stu(1, "zhangsan", 29);

        log.debug(stu.toString());
        return stu;
    }
}

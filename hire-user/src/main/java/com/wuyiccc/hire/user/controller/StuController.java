package com.wuyiccc.hire.user.controller;

import com.wuyiccc.hire.common.CommonResult;
import com.wuyiccc.hire.pojo.test.Stu;
import com.wuyiccc.hire.user.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2023/6/22 21:00
 */
@RestController
@RequestMapping("/stu")
public class StuController {


    @Autowired
    private StuService stuService;

    @GetMapping("/stu")
    public CommonResult<String> stu() {
        Stu stu = new Stu();
//        stu.setId("10001");
        stu.setName("wuyiccc");
        stu.setAge(25);

        stuService.saveStu(stu);
        return CommonResult.ok("创建成功");
    }
}

package com.wuyiccc.tianxuan.auth.service;

import com.wuyiccc.tianxuan.pojo.User;

import javax.jws.soap.SOAPBinding;

/**
 * @author wuyiccc
 * @date 2023/6/27 22:42
 */
public interface UserService {

    public User queryUserByMobile(String mobile);

    public User createUser(String mobile);
}

package com.wuyiccc.tianxuan.auth;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.pojo.test.Stu;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;

/**
 * @author wuyiccc
 * @date 2023/6/29 23:41
 */
@SpringBootTest
@Slf4j
public class TianxuanApplicationTest {


    private static final String USER_KEY = "wuyiccc123iiizzq123456789";

    @Test
    public void createJWT() {
        // 1. 对秘钥进行base64编码
        String base64 = new BASE64Encoder().encode(USER_KEY.getBytes());
        // 2. 对base64生成一个秘钥的对象
        SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes());
        // 3. jwt生成token字符串
        Stu stu = new Stu("1001", "wuyiccc", 18);
        String stuJson = new Gson().toJson(stu);
        String myJWT = Jwts.builder()
                .setSubject(stuJson) // 设置用户自定义数据
                .signWith(secretKey) // 使用哪个秘钥对象进行jwt对象生成
                .compact(); // 压缩并且生成jwt
        log.info("myJWT: {}", myJWT);
    }


}

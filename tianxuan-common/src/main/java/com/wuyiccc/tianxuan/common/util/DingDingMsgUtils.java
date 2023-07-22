package com.wuyiccc.tianxuan.common.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.wuyiccc.tianxuan.common.config.DingDingConfig;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @author wuyiccc
 * @date 2023/7/2 08:14
 */
@Component
@Slf4j
public class DingDingMsgUtils {

    @Autowired
    private DingDingConfig dingDingConfig;

    public void sendSMSCode(String smsCode) {


        try {
            long timestamp = System.currentTimeMillis();
            String secret = dingDingConfig.getSecret();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = null;
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");


            String url = dingDingConfig.getRobotAccessUrl();
            url = url + "&timestamp=" + timestamp + "&sign=" + sign;

            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("本次登录验证码为: " + smsCode);
            request.setText(text);
//        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//        at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
// isAtAll类型如果不为Boolean，请升级至最新SDK
//        at.setIsAtAll(true);
//        at.setAtUserIds(Arrays.asList("109929","32099"));
//        request.setAt(at);

//        request.setMsgtype("link");
//        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//        link.setMessageUrl("https://www.dingtalk.com/");
//        link.setPicUrl("");
//        link.setTitle("时代的火车向前开");
//        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//        request.setLink(link);

//        request.setMsgtype("markdown");
//        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//        markdown.setTitle("杭州天气");
//        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
//                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
//                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
//                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
//        request.setMarkdown(markdown);
            OapiRobotSendResponse response = client.execute(request);
            log.info("钉钉消息通知发送返回信息: {}", response.getMessage());

        } catch (Exception e) {

            log.error("钉钉消息通知发送失败: {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

    }
}

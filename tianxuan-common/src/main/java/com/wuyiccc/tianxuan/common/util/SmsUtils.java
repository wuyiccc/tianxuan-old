package com.wuyiccc.tianxuan.common.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.wuyiccc.tianxuan.common.config.TencentCloudConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2023/6/24 16:25
 */
@Component
@Slf4j
public class SmsUtils {


    @Autowired
    private TencentCloudConfig tencentCloudConfig;

    public void sendSMS(String phone, String code) throws Exception {
        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
         * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
         * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
         * 以免泄露密钥对危及你的财产安全。
         * CAM密匙查询获取: https://console.cloud.tencent.com/cam/capi*/
        Credential cred = new Credential(tencentCloudConfig.getSecretId(),
                tencentCloudConfig.getSecretKey());

        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();

//            httpProfile.setReqMethod("POST"); // 默认使用POST

        /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
         * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        // 实例化一个client选项
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);

        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = {"+86" + phone};//电话号码
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppId("1400568450");   // 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
        req.setSignName("天璇平台");         // 签名
        req.setTemplateId("1108902");       // 模板id：必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看

        /* 模板参数（自定义占位变量）: 若无模板参数，则设置为空 */
        String[] templateParamSet1 = {code};
        req.setTemplateParamSet(templateParamSet1);

        // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SendSmsResponse resp = client.SendSms(req);
        log.info("sms返回信息: {}", resp);
    }
}


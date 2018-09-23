package com.hengxunda.springcloud.nio.baidu;


import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.lss.LssClient;

public abstract class BaiduCloud {

    public static final LssClient LSS_CLIENT;

    private static final String ACCESS_KEY_ID = "your accessKeyId";

    private static final String SECRET_KEY = "your secretKey";

    private static final BceCredentials BAIDU_CONFIG = new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_KEY);

    static {
        BceClientConfiguration config = new BceClientConfiguration();
        config.setCredentials(BAIDU_CONFIG);
        config.setConnectionTimeoutInMillis(3000);
        config.setSocketTimeoutInMillis(3000);
        LSS_CLIENT = new LssClient(config);
    }
}

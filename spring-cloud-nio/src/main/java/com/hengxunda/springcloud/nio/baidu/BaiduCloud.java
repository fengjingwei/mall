package com.hengxunda.springcloud.nio.baidu;


import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.lss.LssClient;
import com.google.common.collect.Maps;

import java.util.Map;

public abstract class BaiduCloud {

    public static final LssClient lssClient;

    private static final String accessKeyId = "your accessKeyId";

    private static final String secretKey = "your secretKey";

    private static final BceCredentials baiduConfig = new DefaultBceCredentials(accessKeyId, secretKey);

    static {
        BceClientConfiguration config = new BceClientConfiguration();
        config.setCredentials(baiduConfig);
        config.setConnectionTimeoutInMillis(3000);
        config.setSocketTimeoutInMillis(3000);
        lssClient = new LssClient(config);
    }

    public static class FileNameCache {

        private static final Map<String, String> fileNamePlayUrl = Maps.newHashMap();

        public static void put(String fileName, String mediaId) {
            fileNamePlayUrl.put(fileName, mediaId);
        }

        public static boolean contains(String fileName) {
            return fileNamePlayUrl.containsKey(fileName);
        }

        public static String get(String fileName) {
            return fileNamePlayUrl.get(fileName);
        }
    }
}

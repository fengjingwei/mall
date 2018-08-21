package com.hengxunda.springcloud.nio.baidu;

import com.baidubce.services.lss.model.CreateStreamResponse;
import com.baidubce.services.lss.model.GetStreamResponse;
import com.baidubce.services.lss.model.ListStreamResponse;

public interface BaiduCloudService {

    CreateStreamResponse createPushStream(String yourPlayDomain, String yourApp, String yourStream);

    ListStreamResponse listStream(String yourPlayDomain, String status);

    GetStreamResponse getStream(String yourPlayDomain, String yourApp, String yourStream);
}

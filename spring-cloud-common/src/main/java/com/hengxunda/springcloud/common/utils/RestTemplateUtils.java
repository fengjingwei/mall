package com.hengxunda.springcloud.common.utils;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@SpringBootConfiguration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public abstract class RestTemplateUtils {

    private static int maxTotalConnect = 200;

    private static int maxConnectPerRoute = maxTotalConnect >> 2;

    private static ClientHttpRequestFactory createFactory() {
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxTotalConnect)
                .setMaxConnPerRoute(maxConnectPerRoute)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(3000);
        return factory;
    }

    @Bean
    public static RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    @ConditionalOnMissingBean(value = {RestTemplate.class})
    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(createFactory());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

        // 重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (Optional.ofNullable(converterTarget).isPresent()) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converterList.add(2, new FormHttpMessageConverter());
        converterList.add(new FastJsonHttpMessageConverter());
        return restTemplate;
    }

    public static void main(String[] args) {
        String body = getRestTemplate().getForObject("http://www.sojson.com/open/api/weather/json.shtml?city=长沙市", String.class);
        System.out.println(body);
    }

}

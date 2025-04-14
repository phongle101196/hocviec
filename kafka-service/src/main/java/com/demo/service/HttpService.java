package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableAsync
public class HttpService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Async
    public void forwardRequest(
            RestTemplate restTemplate,
            String url,
            HttpMethod method,
            HttpEntity<?> httpEntity,
            String module) {
        try {
            Object result = restTemplate.exchange(url, method, httpEntity, Object.class).getBody();
            logger.debug("Sent {} request[{}] : result=[{}] ", module, url, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

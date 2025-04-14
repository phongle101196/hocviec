package com.demo.component;

import com.demo.config.HeaderRequestInterceptor;
import com.demo.dto.HttpServiceDto;
import com.demo.service.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeConsumerComponent {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private HttpService httpService;

    @Autowired
    private RestTemplate restTemplate;
    @KafkaListener(topics = "EMPLOYEE_SERVICE_2", groupId = "group-employee",
            concurrency = "3")
    public void listen(HttpServiceDto httpServiceDto) {
        String url = this.env.getProperty("service.employee").concat(httpServiceDto.getUrl());
        String topic = this.env.getProperty("EMPLOYEE_SERVICE_2");
        logger.info("Received [topic={}] message: [{}] with data [{}]", topic, url, (HashMap<?, ?>) httpServiceDto.getBody());
        HttpMethod method;
        switch (httpServiceDto.getMethod().toUpperCase()) {
            case "GET":
                method = HttpMethod.GET;
                break;
            case "POST":
                method = HttpMethod.POST;
                break;
            case "DELETE":
                method = HttpMethod.DELETE;
                break;
            case "PUT":
                method = HttpMethod.PUT;
                break;
            case "PATCH":
                method = HttpMethod.PATCH;
                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                requestFactory.setConnectTimeout(10000);
                requestFactory.setReadTimeout(10000);
                restTemplate.setRequestFactory(requestFactory);
                break;
            default:
                logger.error("Unsupported HTTP method: {}", httpServiceDto.getMethod());
                throw new IllegalArgumentException("Unsupported HTTP method: " + httpServiceDto.getMethod());
        }
        HttpEntity<?> httpEntity = new HttpEntity<Object>(new Object());
        if (httpServiceDto.getBody() != null)
            httpEntity = new HttpEntity<Object>(httpServiceDto.getBody());
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        if (httpServiceDto.getHeaders() != null) {
            Map<String, String> headers = httpServiceDto.getHeaders();
            headers.forEach((k, v) -> {
                interceptors.add(new HeaderRequestInterceptor(k, v));
            });
        }
        restTemplate.setInterceptors(interceptors);
        this.httpService.forwardRequest(restTemplate, url, method, httpEntity, "employee");
    }
}

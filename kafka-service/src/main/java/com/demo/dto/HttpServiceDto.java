package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpServiceDto {
    private String url;
    private String method;
    private Object body;
    private Map<String, String> headers;
}

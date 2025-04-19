package com.demo.validation.model;

import java.util.List;

public class URLRule extends AttributeRule {
    private List<String> schemes;

    public URLRule(List<String> schemes) {
        super("URL");
        this.schemes = schemes;
    }

    public List<String> getSchemes() {
        return schemes;
    }
}
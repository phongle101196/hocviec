package com.demo.validation.model;

public abstract class AttributeRule {
    private String type;

    public AttributeRule(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
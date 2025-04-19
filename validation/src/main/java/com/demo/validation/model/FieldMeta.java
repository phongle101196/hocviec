package com.demo.validation.model;

import java.util.ArrayList;
import java.util.List;

public class FieldMeta {
    private String fieldName;
    private String springFieldType;
    private String fieldType;
    private List<AttributeRule> attributes = new ArrayList<>();

    public FieldMeta(String fieldName, String springFieldType, String fieldType) {
        this.fieldName = fieldName;
        this.springFieldType = springFieldType;
        this.fieldType = fieldType;
    }
    public FieldMeta(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSpringFieldType() {
        return springFieldType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public List<AttributeRule> getAttributes() {
        return attributes;
    }

    public void addAttribute(AttributeRule attribute) {
        attributes.add(attribute);
    }
}
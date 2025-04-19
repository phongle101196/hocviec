package com.demo.validation.model;

import java.util.ArrayList;
import java.util.List;

public class ClassMeta {
    private String springClassName;
    private String className;
    private List<FieldMeta> fields = new ArrayList<>();

    public ClassMeta(String springClassName, String className) {
        this.springClassName = springClassName;
        this.className = className;
    }

    public String getSpringClassName() {
        return springClassName;
    }

    public String getClassName() {
        return className;
    }

    public List<FieldMeta> getFields() {
        return fields;
    }

    public void addField(FieldMeta field) {
        fields.add(field);
    }
}
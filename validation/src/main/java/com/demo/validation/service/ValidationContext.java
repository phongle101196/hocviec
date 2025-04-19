package com.demo.validation.service;

import com.demo.validation.model.ClassMeta;

import java.util.HashMap;
import java.util.Map;

public class ValidationContext {
    private final Map<String, ClassMeta> context = new HashMap<>();

    public void addClassMeta(String className, ClassMeta classMeta) {
        context.put(className, classMeta);
    }

    public ClassMeta getClassMeta(String className) {
        return context.get(className);
    }
}
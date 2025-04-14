package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.ClassMeta;
import com.demo.validation.model.FieldMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ValidationModule {
    private final ValidationContext context;

    public ValidationModule(ValidationContext context) {
        this.context = context;
    }

    public Map<String, List<String>> validate(String className, Map<String, String> requestBody) {
        Map<String, List<String>> errors = new HashMap<>();
        ClassMeta classMeta = context.getClassMeta(className);
        if (classMeta == null) return errors;

        for (FieldMeta field : classMeta.getFields()) {
            String fieldName = field.getFieldName();
            String value = requestBody.getOrDefault(fieldName, "");
            // Print console log hera
            System.out.println("Field Name: " + fieldName + ", Value: " + value);
            long startTime = System.currentTimeMillis();
            for (AttributeRule attribute : field.getAttributes()) {
                System.out.println("Type: " + attribute.getType());
                Validator validator = ValidatorFactory.getInstance().getValidator(attribute.getType());
                if (validator != null) {
                    StringBuilder errorMessage = new StringBuilder();
                    if (!validator.validate(value, attribute, field, errorMessage)) {
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage.toString());
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            System.out.println("Processing time: " + processingTime + " ms");
        }

        return errors;
    }
}
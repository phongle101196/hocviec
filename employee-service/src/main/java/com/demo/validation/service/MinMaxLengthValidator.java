package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.model.MinMaxLengthRule;

public class MinMaxLengthValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        MinMaxLengthRule rule = (MinMaxLengthRule) attribute;
        int length = value.length();
        if (length < rule.getMin()) {
            errorMessage.append("Length must be at least ").append(rule.getMin()).append(" characters.");
            return false;
        }
        if (length > rule.getMax()) {
            errorMessage.append("Length must be at most ").append(rule.getMax()).append(" characters.");
            return false;
        }
        return true;
    }
}
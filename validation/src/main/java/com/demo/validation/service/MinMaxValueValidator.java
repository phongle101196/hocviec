package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.model.MinMaxValueRule;

public class MinMaxValueValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        MinMaxValueRule rule = (MinMaxValueRule) attribute;
        try {
            float numericValue = Float.parseFloat(value);
            if (numericValue < rule.getMin()) {
                errorMessage.append("Value must be at least ").append(rule.getMin()).append(".");
                return false;
            }
            if (numericValue > rule.getMax()) {
                errorMessage.append("Value must be at most ").append(rule.getMax()).append(".");
                return false;
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Expected a numeric value for field '").append(fieldMeta.getFieldName()).append("'.");
            return false;
        }
        return true;
    }
}
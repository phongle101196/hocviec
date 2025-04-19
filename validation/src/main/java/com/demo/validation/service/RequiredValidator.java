package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

public class RequiredValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        if (value == null || value.isEmpty()) {
            errorMessage.append("The field '").append(fieldMeta.getFieldName()).append("' is required.");
            return false;
        }
        return true;
    }
}
package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

public class AlphaNumericValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        if (value == null) {
            return true; // Other validators handle required fields
        }
        if (!value.matches("[a-zA-Z0-9]+")) {
            errorMessage.append("Value for '").append(fieldMeta.getFieldName()).append("' must be alphanumeric.");
            return false;
        }
        return true;
    }
}
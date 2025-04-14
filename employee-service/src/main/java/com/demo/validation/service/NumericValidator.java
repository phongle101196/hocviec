package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

import java.util.regex.Pattern;

public class NumericValidator implements Validator{
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {

        if (value == null || value.trim().isEmpty()) {
            errorMessage.append("Field '").append(fieldMeta.getFieldName()).append("' cannot be empty.");
            return false;
        }
        if (!NUMBER_PATTERN.matcher(value).matches()) {
            errorMessage.append("Field '").append(fieldMeta.getFieldName()).append("' must contain only numbers.");
            return false;
        }

        return true;
    }
}

package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.model.RegexRule;

import java.util.regex.Pattern;

public class RegexValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        RegexRule rule = (RegexRule) attribute;
        if (value == null) {
            return true; // Other validators handle required fields
        }
        if (!Pattern.matches(rule.getPattern(), value)) {
            errorMessage.append("Value for '").append(fieldMeta.getFieldName()).append("' does not match the required pattern.");
            return false;
        }
        return true;
    }
}
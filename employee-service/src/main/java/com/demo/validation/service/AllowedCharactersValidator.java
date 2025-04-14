package com.demo.validation.service;

import com.demo.validation.model.AllowedCharactersRule;
import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

public class  AllowedCharactersValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        AllowedCharactersRule rule = (AllowedCharactersRule) attribute;
        for (char c : value.toCharArray()) {
            if (rule.getAllowedChars().indexOf(c) == -1) {
                errorMessage.append("Character '").append(c).append("' in field '").append(fieldMeta.getFieldName()).append("' is not allowed.");
                return false;
            }
        }
        if (value.startsWith(" ") || value.endsWith(" ")) {
            errorMessage.append("Field '")
                        .append(fieldMeta.getFieldName())
                        .append("' cannot have leading or trailing spaces.");
            return false;
        }
        if (value.contains("  ")) {
            errorMessage.append("Field '")
                        .append(fieldMeta.getFieldName())
                        .append("' cannot have multiple consecutive spaces.");
            return false;
        }
        return true;
    }
}
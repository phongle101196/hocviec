package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.model.NotAllowedCharactersRule;

public class NotAllowedCharactersValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        NotAllowedCharactersRule rule = (NotAllowedCharactersRule) attribute;
        for (char c : value.toCharArray()) {
            if (rule.getNotAllowedChars().indexOf(c) != -1) {
                errorMessage.append("Character '").append(c).append("' in field '").append(fieldMeta.getFieldName()).append("' is not allowed.");
                return false;
            }
        }
        return true;
    }
}
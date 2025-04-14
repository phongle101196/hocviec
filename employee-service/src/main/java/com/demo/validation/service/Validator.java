package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

public interface Validator {
    boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage);
}
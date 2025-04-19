package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;
import com.demo.validation.model.URLRule;

import java.util.regex.Pattern;

public class URLValidator implements Validator {
    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        URLRule rule = (URLRule) attribute;
        if (value == null) {
            return true; // Other validators handle required fields
        }

        String schemesPattern = String.join("|", rule.getSchemes());
        String urlPattern = "^(?i)(" + schemesPattern + "):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*$";
        if (!Pattern.matches(urlPattern, value)) {
            errorMessage.append("Value for '").append(fieldMeta.getFieldName()).append("' is not a valid URL.");
            return false;
        }
        return true;
    }
}
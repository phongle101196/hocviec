package com.demo.validation.service;

import com.demo.validation.model.AttributeRule;
import com.demo.validation.model.FieldMeta;

import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]{5,}@[a-zA-Z0-9.-]{3,}\\.[a-zA-Z]{3,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean validate(String value, AttributeRule attribute, FieldMeta fieldMeta, StringBuilder errorMessage) {
        // Kiểm tra null hoặc rỗng
        if (value == null || value.trim().isEmpty()) {
            errorMessage.append("Field '").append(fieldMeta.getFieldName()).append("' cannot be empty.");
            return false;
        }

        // Kiểm tra định dạng email
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            errorMessage.append("Field '").append(fieldMeta.getFieldName())
                    .append("' is not a valid email address. ");

            // phân tích chi tiết
            String[] parts = value.split("@");
            if (parts.length != 2) {
                errorMessage.append("Email must contain a single '@' symbol.");
            } else {
                String localPart = parts[0]; //  trước @
                String domainPart = parts[1]; //  sau @

                if (localPart.length() < 5) {
                    errorMessage.append("The part before '@' must be at least 5 characters long. ");
                }

                String[] domainParts = domainPart.split("\\.");
                if (domainParts.length < 2) {
                    errorMessage.append("Domain must contain at least one dot ('.') symbol. ");
                } else {
                    String domainName = domainParts[0]; // trước dấu chấm
                    String topLevelDomain = domainParts[1]; // sau dấu chấm

                    if (domainName.length() < 3) {
                        errorMessage.append("The domain part before '.' must be at least 3 characters long. ");
                    }

                    if (topLevelDomain.length() < 3) {
                        errorMessage.append("The domain part after '.' must be at least 3 characters long. ");
                    }
                }
            }
            return false;
        }

        return true;
    }
}
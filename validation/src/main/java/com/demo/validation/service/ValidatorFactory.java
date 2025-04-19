package com.demo.validation.service;

import com.demo.validation.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ValidatorFactory {
    private static final ValidatorFactory instance = new ValidatorFactory();
    private final Map<String, Validator> validators = new HashMap<>();
    private final Map<String, Function<Map<String, Object>, AttributeRule>> attributeRules = new HashMap<>();

    private ValidatorFactory() {
        registerValidator("MIN_MAX_VALUE", new MinMaxValueValidator());
        registerValidator("MIN_MAX_LENGTH", new MinMaxLengthValidator());
        registerValidator("REGEX", new RegexValidator());
        registerValidator("ALLOWED_CHARACTERS", new AllowedCharactersValidator());
        registerValidator("NOT_ALLOWED_CHARACTERS", new NotAllowedCharactersValidator());
        registerValidator("REQUIRED", new RequiredValidator());
        registerValidator("URL", new URLValidator());
        registerValidator("ALPHA_NUMERIC", new AlphaNumericValidator());
        registerValidator("EMAIL", new EmailValidator());
        registerValidator("NUMERIC", new NumericValidator());

        registerAttributeRule("MIN_MAX_VALUE", params -> new MinMaxValueRule(
                ((Number) params.get("min")).floatValue(),
                ((Number) params.get("max")).floatValue()
        ));
        registerAttributeRule("MIN_MAX_LENGTH", params -> new MinMaxLengthRule(
                ((Number) params.get("min")).intValue(),
                ((Number) params.get("max")).intValue()
        ));
        registerAttributeRule("REGEX", params -> new RegexRule((String) params.get("pattern")));
        registerAttributeRule("ALLOWED_CHARACTERS", params -> new AllowedCharactersRule((String) params.get("allowed_chars")));
        registerAttributeRule("NOT_ALLOWED_CHARACTERS", params -> new NotAllowedCharactersRule((String) params.get("not_allowed_chars")));
        registerAttributeRule("URL", params -> new URLRule((List<String>) params.get("schemes")));
        registerAttributeRule("ALPHA_NUMERIC", null);
        registerAttributeRule("REQUIRED", null);
        registerAttributeRule("EMAIL", params -> new EmailRule());
        registerAttributeRule("NUMERIC", params -> new NumericRule());
    }

    public static ValidatorFactory getInstance() {
        return instance;
    }

    public Validator getValidator(String type) {
        return validators.get(type);
    }

    public AttributeRule getAttributeRule(String type, Map<String, Object> params) {
        Function<Map<String, Object>, AttributeRule> creator = attributeRules.get(type);
        if (creator != null) {
            return creator.apply(params);
        }
        return null;
    }

    private void registerValidator(String type, Validator validator) {
        validators.put(type, validator);
    }

    private void registerAttributeRule(String type, Function<Map<String, Object>, AttributeRule> creator) {
        if (creator != null) {
            attributeRules.put(type, creator);
        } else {
            attributeRules.put(type, params -> new NonAttributeRule(type));
        }
            
    }
}
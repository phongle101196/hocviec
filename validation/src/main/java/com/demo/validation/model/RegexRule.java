package com.demo.validation.model;

public class RegexRule extends AttributeRule {
    private String pattern;

    public RegexRule(String pattern) {
        super("REGEX");
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
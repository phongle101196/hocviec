package com.demo.validation.model;

public class NotAllowedCharactersRule extends AttributeRule {
    private String notAllowedChars;

    public NotAllowedCharactersRule(String notAllowedChars) {
        super("NOT_ALLOWED_CHARACTERS");
        this.notAllowedChars = notAllowedChars;
    }

    public String getNotAllowedChars() {
        return notAllowedChars;
    }
}
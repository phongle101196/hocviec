package com.demo.validation.model;

public class AllowedCharactersRule extends AttributeRule {
    private String allowedChars;

    public AllowedCharactersRule(String allowedChars) {
        super("ALLOWED_CHARACTERS");
        this.allowedChars =
                (allowedChars != null) ?
                 allowedChars : "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ " +
                        "áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ" +
                        "ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ ";
    }

    public String getAllowedChars() {
        return allowedChars;
    }
}
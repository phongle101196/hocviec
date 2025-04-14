package com.demo.util;

public class SpringUtils {
    public static Long convertToLong(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Long.parseLong(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

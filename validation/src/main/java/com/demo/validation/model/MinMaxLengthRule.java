package com.demo.validation.model;

public class MinMaxLengthRule extends AttributeRule {
    private int min;
    private int max;

    public MinMaxLengthRule(int min, int max) {
        super("MIN_MAX_LENGTH");
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
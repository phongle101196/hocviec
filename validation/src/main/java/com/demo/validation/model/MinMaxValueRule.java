package com.demo.validation.model;

public class MinMaxValueRule extends AttributeRule {
    private float min;
    private float max;

    public MinMaxValueRule(float min, float max) {
        super("MIN_MAX_VALUE");
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
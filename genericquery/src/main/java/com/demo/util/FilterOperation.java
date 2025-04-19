package com.demo.util;

import com.demo.exception.InvalidFilterException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FilterOperation {
    EQUALS("equals"),
    NOT_EQUALS("not_equals"),
    CONTAINS("contains"),
    DOES_NOT_CONTAIN("does_not_contain"),
    STARTS_WITH("starts_with"),
    ENDS_WITH("ends_with"),
    IS_EMPTY("is_empty"),
    IS_NOT_EMPTY("is_not_empty"),
    GREATER_THAN("greater_than"),
    LESS_THAN("less_than");

    private final String operation;

    public static FilterOperation fromString(String operation){
        for (FilterOperation op: FilterOperation.values()) {
            if (op.operation.equalsIgnoreCase(operation)){
                return op;
            }
        }
        throw new InvalidFilterException("Invalid operation: " +operation);
    }

}

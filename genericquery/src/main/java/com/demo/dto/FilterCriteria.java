package com.demo.dto;

import com.demo.util.FilterOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCriteria {
    private String key;
    private FilterOperation operation;
    private Object value;
}

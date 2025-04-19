package com.demo.util;

import com.demo.dto.FilterCriteria;
import com.demo.exception.InvalidFilterException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    public enum CombineOperation {
        AND,
        OR
    }
    private final List<Specification<T>> specs = new ArrayList<>();
    private CombineOperation combineOperation = CombineOperation.AND;

    public SpecificationBuilder<T> with(FilterCriteria criteria){
        specs.add(createSpecification(criteria));
        return this;
    }
    public Specification<T> build(){
        if (specs.isEmpty()){
            return ((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        }
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++){
            if (combineOperation == CombineOperation.AND) {
                result = result.and(specs.get(i));
            }else {
                result = result.or(specs.get(i));
            }
        }
        return result;
    }
    private Specification<T> createSpecification(FilterCriteria criteria){
        return ((root, query, builder) -> {
            String key = criteria.getKey();
            Object value = criteria.getValue();
            FilterOperation operation = criteria.getOperation();
            try {
                root.get(key);
            }catch (IllegalArgumentException e){
                throw new InvalidFilterException("Field '" + key + "' does not exist");
            }
            switch (operation) {
                case EQUALS:
                    return builder.equal(root.get(key), value);
                case NOT_EQUALS:
                    return builder.notEqual(root.get(key), value);
                case CONTAINS:
                    if (value == null) {
                        throw new InvalidFilterException("Value cannot be null for CONTAINS operation");
                    }
                    if (!(value instanceof String)){
                        throw new InvalidFilterException("Value must be a string for CONTAINS operation");
                    }
                    return builder.like(builder.lower(root.get(key)), "%" + value.toString().toLowerCase() + "%");
                case DOES_NOT_CONTAIN:
                    if (value == null)
                        throw new InvalidFilterException("Value cannot be null for DOES_NOT_CONTAIN operation");
                    return builder.notLike(builder.lower(root.get(key)), "%" + value.toString().toLowerCase() + "%");
                case STARTS_WITH:
                    if (value == null)
                        throw new InvalidFilterException("Value cannot be null for STARTS_WITH operation");
                    return builder.like(builder.lower(root.get(key)), value.toString().toLowerCase() + "%");
                case ENDS_WITH:
                    if (value == null)
                        throw new InvalidFilterException("Value cannot be null for ENDS_WITH operation");
                    return builder.like(builder.lower(root.get(key)), "%" + value.toString().toLowerCase());
                case IS_EMPTY:
                    return builder.or(
                            builder.isNull(root.get(key)),
                            builder.equal(root.get(key), "")
                    );
                case IS_NOT_EMPTY:
                    return builder.and(
                            builder.isNull(root.get(key)),
                            builder.equal(root.get(key), "")
                    );
                case GREATER_THAN:
                    if (value == null){
                        throw new InvalidFilterException("Value cannot be null for GREATER_THAN operation");
                    }
                    if (!(value instanceof Comparable)){
                        throw new InvalidFilterException("Value must be of a comparable type (e.g., number, date)");
                    }
                    return builder.greaterThan(root.get(key), (Comparable) value);
                case LESS_THAN:
                    if (value == null){
                        throw new InvalidFilterException("Value cannot be null for LESS_THAN operation");
                    }
                    if (!(value instanceof Comparable)){
                        throw new InvalidFilterException("Value must be of a comparable type (e.g., number, date)");
                    }
                    return builder.lessThan(root.get(key), (Comparable) value);
                default:
                    throw new UnsupportedOperationException("Operation " + operation + " not supported");
            }
        });
    }
}

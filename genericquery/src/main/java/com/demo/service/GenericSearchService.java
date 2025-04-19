package com.demo.service;

import com.demo.dto.FilterCriteria;
import com.demo.util.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericSearchService<T> {
    public List<T> search(List<FilterCriteria> params, JpaSpecificationExecutor<T> repository){
        SpecificationBuilder<T> builder = new SpecificationBuilder<>();
        params.forEach(builder::with);
        Specification<T> spec = builder.build();
        return repository.findAll(spec);
    }
}

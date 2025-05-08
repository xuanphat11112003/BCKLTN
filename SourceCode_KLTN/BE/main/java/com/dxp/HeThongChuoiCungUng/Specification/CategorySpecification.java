package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.persistence.criteria.Predicate;

public class CategorySpecification {
    public static Specification<Category> Search(Map<String,String> params){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(params.containsKey("name") && !params.get("name").isEmpty()){
                String name = params.get("name");
                predicates.add(criteriaBuilder.like(root.get("name"),String.format("%%%s%%",name)));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
}

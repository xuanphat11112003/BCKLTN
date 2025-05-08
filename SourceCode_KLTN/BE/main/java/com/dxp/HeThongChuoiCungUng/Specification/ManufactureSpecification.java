package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.Manufacture;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManufactureSpecification {
    public static Specification<Manufacture> Search(Map<String,String> params){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(params.containsKey("kw") && !params.get("kw").isEmpty()){
                int id = Integer.parseInt(params.get("kw"));
                predicates.add(criteriaBuilder.equal(root.get("id"),id));
            }
            if(params.containsKey("productId")&& !params.get("productId").isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("productId").get("id"),Integer.parseInt(params.get("productId"))));
            }
            if(params.containsKey("productName")&& !params.get("productName").isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("productName").get("name"),String.format("%%%s%%",params.get("productId"))));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
}

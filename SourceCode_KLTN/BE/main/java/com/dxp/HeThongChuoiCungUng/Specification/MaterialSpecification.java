package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.Material;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialSpecification {
    public static Specification<Material> Search(Map<String,String> params){
        return ((root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if(params.containsKey("name")&& !params.get("name").isEmpty()){
                String name = params.get("name");
                predicates.add(criteriaBuilder.like(root.get("name"),String.format("%%%s%%",name)));
            }
            if (params.containsKey("category-id") && !params.get("category-id").isEmpty()){
                String CategoryId = params.get("category-id");
                if(CategoryId != null && !CategoryId.isEmpty())
                    predicates.add(criteriaBuilder.equal(root.get("categoryId").get("id"),Integer.parseInt(CategoryId)));
            }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        );


    }

}

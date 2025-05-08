package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.*;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportOrderSpecification  {
    public static Specification<Importorder> JoinImportOrder(Map<String,String> params){
        return ((root, query, criteriaBuilder) -> {
            Join<Importorder, Detailimportorder> detailJoin = root.join("detailimportorderCollection");
            Join<Detailimportorder, Material> materialJoin = detailJoin.join("materialID");
            Join<Material, Supplier> supplierJoin = materialJoin.join("supplierId");
            Join<Importorder, User> userJoin = root.join("importedBy");

            query.multiselect(
                    root.get("id"),
                    root.get("orderDate"),
                    root.get("totalCost"),
                    root.get("payment"),
                    userJoin.get("username"),
                    criteriaBuilder.sum(detailJoin.get("quantity").as(Long.class)),
                    criteriaBuilder.sum(detailJoin.get("totalAmount").as(Double.class)),
                    supplierJoin.get("name"),
                    materialJoin.get("name"),
                    materialJoin.get("id"),
                    root.get("active"),
                    root.get("activeEvaluate")
            );
            query.groupBy(
                    root.get("id"),
                    root.get("orderDate"),
                    root.get("totalCost"),
                    root.get("payment"),
                    userJoin.get("username"),
                    supplierJoin.get("name"),
                    materialJoin.get("name"),
                    materialJoin.get("id")
            );
            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("orderId") && !params.get("orderId").isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), Integer.parseInt(params.get("orderId"))));
            }

            if (params.containsKey("active") && !params.get("active").isEmpty()) {
                boolean active = Boolean.parseBoolean(params.get("active"));
                predicates.add(criteriaBuilder.equal(root.get("active"), active));
            }

            if (params.containsKey("payment")&& !params.get("payment").isEmpty()) {
                String payment = params.get("payment");
                predicates.add(criteriaBuilder.equal(root.get("payment"), payment));
            }

            if (params.containsKey("orderDateSort")&& !params.get("orderDateSort").isEmpty()) {
                String sortOrder = params.get("orderDateSort");
                if ("asc".equalsIgnoreCase(sortOrder)) {
                    query.orderBy(criteriaBuilder.asc(root.get("orderDate")));
                } else if ("desc".equalsIgnoreCase(sortOrder)) {
                    query.orderBy(criteriaBuilder.desc(root.get("orderDate")));
                }
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }

}

package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportOrderSpecification {
    public static Specification<Oderexport> JoinExportOrder(Map<String, String> params){
        return ((root, query, criteriaBuilder) -> {
            Join<Oderexport, Detailexportorder> detailexportorderJoin = root.join("detailexportorderCollection");
            Join<Detailexportorder, Product> productJoin = detailexportorderJoin.join("productId");
            Join<Oderexport, Detailsexportordercost> detailsexportordercostJoin = root.join("detailsexportordercostCollection");
            Join<Detailsexportordercost, Cost> costJoin = detailsexportordercostJoin.join("cost");
            Join<Oderexport,Agency> agencyJoin = root.join("order_for");
            Join<Oderexport,User> userJoin = root.join("order_by");

            Join<Oderexport, Shipment> transportJoin = null;
            if (params.containsKey("includeTransport") && params.get("includeTransport").equals("true")) {
                transportJoin = root.join("transport", JoinType.LEFT);
            }

            query.multiselect(
                    root.get("id"),
                    root.get("typeBuy"),
                    root.get("state"),
                    root.get("createdDate"),
                    root.get("totalPrice"),
                    productJoin.get("name"),
                    productJoin.get("id"),
                    costJoin.get("id"),
                    costJoin.get("name"),
                    costJoin.get("type"),
                    costJoin.get("price"),
                    detailsexportordercostJoin.get("description"),
                    agencyJoin.get("userAID").get("username"),
                    agencyJoin.get("id"),
                    userJoin.get("username")
            );
            if (transportJoin != null) {
                query.multiselect(
                        transportJoin.get("expressId").get("id"),
                        transportJoin.get("expressId").get("name")
                );
            }
            query.groupBy(
                    root.get("id"),
                    productJoin.get("id"),
                    costJoin.get("id"),
                    agencyJoin.get("id"),
                    userJoin.get("username")
            );
            List<Predicate> predicates = new ArrayList<>();
            if (params.containsKey("orderId") && !params.get("orderId").isEmpty()) {
                String orderId = params.get("orderId");
                predicates.add(criteriaBuilder.equal(root.get("id"), orderId));
            }
            if (params.containsKey("agencyName") && !params.get("agencyName").isEmpty()) {
                String agencyName = params.get("agencyName");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(agencyJoin.get("userAID").get("username")), "%" + agencyName.toLowerCase() + "%"));
            }
            if (params.containsKey("state")) {
                String state = params.get("state");
                predicates.add(criteriaBuilder.equal(root.get("state"), state));
            }
            String sortOrder = params.getOrDefault("sortOrder", "asc");
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
}

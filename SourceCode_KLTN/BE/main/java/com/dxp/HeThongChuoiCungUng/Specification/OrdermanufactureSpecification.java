package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.OrderDetailManufacture;
import com.dxp.HeThongChuoiCungUng.Model.OrderManufacture;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdermanufactureSpecification {
    public static Specification<OrderManufacture> getOrderManufacture(Map<String,String> params){
        return ((root, query, criteriaBuilder) -> {
            Join<OrderManufacture, Warehouse> warehouseJoin = root.join("warehouseID");
            Join<OrderManufacture, OrderDetailManufacture> detailManufactureJoin = root.join("orderDetailManufactureCollection");
            Join<OrderDetailManufacture, Product> productJoin = detailManufactureJoin.join("product");
            query.multiselect(
                    root.get("id"),
                    root.get("createDate"),
                    root.get("transaction"),
                    root.get("active"),
                    root.get("order_by"),
                    warehouseJoin.get("name"),
                    criteriaBuilder.sum(detailManufactureJoin.get("quantity")),
                    productJoin.get("name"),
                    productJoin.get("id")
            );
            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("id") && !params.get("id").isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), Integer.parseInt(params.get("id"))));
            }

            if (params.containsKey("transaction") && !params.get("transaction").isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("transaction"), String.format("%%%s%%",params.get("transaction"))));
            }
            if (params.containsKey("warehouseName") && !params.get("warehouseName").isEmpty()) {
                predicates.add(criteriaBuilder.notEqual(root.get("transaction"), OrderManufacture.transactions.V1.getValue()));
                predicates.add(criteriaBuilder.like(warehouseJoin.get("name"), String.format("%%%s%%", params.get("warehouseName"))));
            }

            query.groupBy(
                    root.get("id"),
                    productJoin.get("name"),
                    productJoin.get("id"),
                    root.get("order_by"),
                    warehouseJoin.get("name")
            );


            if (params.containsKey("sortDirection") && params.get("sortDirection").equalsIgnoreCase("desc")) {
                query.orderBy(criteriaBuilder.desc(root.get("createDate")));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get("createDate")));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
}

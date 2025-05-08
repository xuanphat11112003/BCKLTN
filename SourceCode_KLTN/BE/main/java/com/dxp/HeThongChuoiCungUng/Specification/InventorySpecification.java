package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventorySpecification {
    public static Specification<Inventory> getAllAsc(Map<String,String> params) {
        return ((root, query, criteriaBuilder) -> {
            Join<Inventory, Materialstock> materialstockJoin = root.join("materialstockCollection");
            Join<Materialstock, Material> materialJoin = materialstockJoin.join("materialId");
            Join<Inventory, Warehouse> warehouseJoin = root.join("warehouse");
            query.multiselect(
                    root.get("id"),
                    root.get("entryDate"),
                    criteriaBuilder.sum(materialstockJoin.get("amount")),
                    materialJoin.get("name"),
                    materialJoin.get("id"),
                    warehouseJoin.get("id"),
                    warehouseJoin.get("name")
            );

            List<Predicate> predicates = new ArrayList<>();
            if(params.containsKey("materialId") && !params.get("materialId").isEmpty()){
                predicates.add(criteriaBuilder.equal(materialJoin.get("id"),Integer.parseInt(params.get("materialId"))));
            }
            if(params.containsKey("warehouseId") && !params.get("warehouseId").isEmpty()){
                predicates.add(criteriaBuilder.equal(warehouseJoin.get("id"),Integer.parseInt(params.get("warehouseId"))));
            }
            query.groupBy(
                    root.get("id"),
                    materialJoin.get("name"),
                    materialJoin.get("id"),
                    warehouseJoin.get("id"),
                    warehouseJoin.get("name")
            );
            query.orderBy(criteriaBuilder.asc(root.get("entryDate")));

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));

        });

    }
    public static Specification<Inventory> searchByStockType(Map<String, String> params, int id) {
        return (root, query, criteriaBuilder) -> {
            Join<Inventory,Warehouse> warehouseJoin = root.join("warehouse");

            List<Predicate> predicates = new ArrayList<>();

            String stockType = params.get("stockType");
            String name = params.get("name");
            predicates.add(criteriaBuilder.equal(warehouseJoin.get("id"), id));
            if ("materialstock".equalsIgnoreCase(stockType)) {
                Join<Inventory, Materialstock> materialStockJoin = root.join("materialstockCollection");
                predicates.add(criteriaBuilder.isNotNull(materialStockJoin.get("id")));

                if (name != null && !name.isEmpty()) {
                    predicates.add(criteriaBuilder.like(materialStockJoin.get("name"), String.format("%%%s%%",name)));
                }
            } else if ("productstock".equalsIgnoreCase(stockType)) {
                Join<Inventory, Productstock> productStockJoin = root.join("productstockCollection");
                predicates.add(criteriaBuilder.isNotNull(productStockJoin.get("id")));

                if (name != null && !name.isEmpty()) {
                    predicates.add(criteriaBuilder.like(productStockJoin.get("name"), String.format("%%%s%%",name)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

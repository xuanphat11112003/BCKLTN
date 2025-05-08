package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WareHouseSpecification {
    public static Specification<Warehouse> Search (Map<String,String> params){

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(params.containsKey("name")&& !params.get("name").isEmpty()){
                String name = params.get("name");
                predicates.add(criteriaBuilder.like(root.get("name"),String.format("%%%s%%",name)));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
    public static Specification<Warehouse> getOrderInWarehouse(int id, Map<String,String> params){
        return ((root, query, criteriaBuilder) -> {
            Join<Warehouse, Logistics> logisticsJoin = root.join("logisticsCollection");
            Join<Logistics, Oderexport> oderexportJoin = logisticsJoin.join("Logistcs");
            Join<Oderexport, Detailexportorder> detailexportorderJoin = oderexportJoin.join("detailexportorderCollection");
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("id"), id));
            query.multiselect(
                    root.get("id"),
                    root.get("name"),
                    detailexportorderJoin.get("productId").get("name"),
                    logisticsJoin.get("type")
            );
            String orderId = params.get("orderId");
            if (orderId != null && !orderId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(oderexportJoin.get("id"), Long.valueOf(orderId)));
            }
            String logisticsType = params.get("logisticsType"); // Giả sử bạn gửi "logisticsType" trong params
            if (logisticsType != null && !logisticsType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(logisticsJoin.get("type"), logisticsType));
            }else{
                Predicate comingPredicate = criteriaBuilder.equal(logisticsJoin.get("type"), "coming");
                Predicate arrivedPredicate = criteriaBuilder.equal(logisticsJoin.get("type"), "arrived");
                Predicate combinedPredicate = criteriaBuilder.or(comingPredicate, arrivedPredicate);
                predicates.add(combinedPredicate);
            }

            Predicate notHasLeftPredicate = criteriaBuilder.notEqual(logisticsJoin.get("type"), "has_left");
            predicates.add(notHasLeftPredicate);
            query.groupBy(root.get("id"), detailexportorderJoin.get("productId").get("name"),logisticsJoin.get("type"));

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }
}

package com.dxp.HeThongChuoiCungUng.Specification;

import com.dxp.HeThongChuoiCungUng.Model.Detailexportorder;
import com.dxp.HeThongChuoiCungUng.Model.Oderexport;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import java.util.Date;

public class DetailExportSpecification {
    public static Specification<Detailexportorder> filterByProductAndMonth(Date startDate, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            Join<Detailexportorder, Oderexport> oderexportJoin = root.join("exportOrderId", JoinType.INNER);
            Join<Detailexportorder, Product> productJoin = root.join("productId", JoinType.INNER);

            Predicate predicate = criteriaBuilder.conjunction();
            if (startDate != null && endDate != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.between(oderexportJoin.get("createdDate"), startDate, endDate)
                );
            }
            Expression<Integer> yearExp = criteriaBuilder.function("YEAR", Integer.class, oderexportJoin.get("createdDate"));
            Expression<Integer> monthExp = criteriaBuilder.function("MONTH", Integer.class, oderexportJoin.get("createdDate"));
            query.multiselect(
                    productJoin.get("id"),
                    root.get("id"),
                    yearExp,
                    monthExp,
                    criteriaBuilder.sum(root.get("amount"))

            );
            query.groupBy(productJoin.get("id"),root.get("id"), yearExp, monthExp);

            return predicate;
        };
    }
}

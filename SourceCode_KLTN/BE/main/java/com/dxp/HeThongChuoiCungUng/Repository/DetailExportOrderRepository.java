package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Detailexportorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DetailExportOrderRepository extends JpaRepository<Detailexportorder,Long>, JpaSpecificationExecutor<Detailexportorder> {
    @Query("SELECT p.id, YEAR(o.createdDate), MONTH(o.createdDate), SUM(d.amount) " +
            "FROM Detailexportorder d " +
            "JOIN d.exportOrderId o " +
            "JOIN d.productId p " +
            "WHERE o.createdDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.id, YEAR(o.createdDate), MONTH(o.createdDate)")
    List<Object[]> getSalesDataByProduct(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

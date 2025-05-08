package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.Model.Importorder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImportOrderRepository extends JpaRepository<Importorder,Long>, JpaSpecificationExecutor<Importorder> {
    public Importorder findById(int id);
    @Query(value = "SELECT (WEEK(i.order_date) - WEEK(DATE_SUB(i.order_date, INTERVAL DAY(i.order_date) - 1 DAY)) + 1) AS weekInMonth, " +
            "COALESCE(SUM(i.total_cost), 0) AS totalCost " +
            "FROM Importorder i " +
            "WHERE MONTH(i.order_date) = :month AND YEAR(i.order_date) = :year " +
            "GROUP BY weekInMonth",
            nativeQuery = true)
    List<Object[]> findTotalCostByWeek(@Param("month") int month, @Param("year") int year);

    @Query("SELECT MONTH(i.orderDate), SUM(i.totalCost) FROM Importorder i WHERE YEAR(i.orderDate) = :year GROUP BY MONTH(i.orderDate)")
    List<Object[]> findMonthlyTotalCost(@Param("year") int year);

//    @Query("SELECT io.id, io.orderDate, io.totalCost, io.payment, " +
//            "u.username, SUM(di.quantity), SUM(di.totalAmount), COALESCE(s.name, 'N/A'),COALESCE(m.name, 'N/A')," +
//            "COALESCE(m.id, 'N/A'), io.active, io.activeEvaluate " +
//            "FROM Importorder io " +
//            "JOIN io.detailimportorderCollection di " +
//            "JOIN di.materialID m " +
//            "LEFT JOIN m.supplierId s " +
//            "JOIN io.importedBy u " + // Giả sử `createdBy` là mối quan hệ đến `User`
//            "GROUP BY io.id, io.orderDate, io.totalCost, io.payment, u.username, s.name,m.name,m.id")
//    List<Object[]> findOrderDetailsGrouped();
}

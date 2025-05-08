package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Oderexport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderExportRepository extends JpaRepository<Oderexport,Long> , JpaSpecificationExecutor<Oderexport> {
    Oderexport findById(int id);
    @Query(value = "SELECT (WEEK(o.created_date) - WEEK(DATE_SUB(o.created_date, INTERVAL DAY(o.created_date) - 1 DAY)) + 1) AS weekInMonth, " +
            "COALESCE(SUM(o.total_price), 0) AS totalRevenue " +
            "FROM Oderexport o " +
            "WHERE MONTH(o.created_date) = :month AND YEAR(o.created_date) = :year " +
            "GROUP BY weekInMonth",
            nativeQuery = true)
    List<Object[]> findTotalRevenueByWeek(@Param("month") int month, @Param("year") int year);

    @Query("SELECT MONTH(o.createdDate), SUM(o.totalPrice) FROM Oderexport  o WHERE YEAR(o.createdDate) = :year GROUP BY MONTH(o.createdDate)")
    List<Object[]> findMonthlyTotalRevenue(@Param("year") int year);

    @Query("SELECT o FROM Oderexport o WHERE o.order_for.id = :orderForId ")
    List<Oderexport> findOrdersByOrderForAndState(
            @Param("orderForId") Integer orderForId);
}

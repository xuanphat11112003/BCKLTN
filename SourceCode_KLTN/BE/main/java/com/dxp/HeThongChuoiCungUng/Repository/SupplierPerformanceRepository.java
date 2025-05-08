package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Supplierperformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierPerformanceRepository extends JpaRepository<Supplierperformance,Long> {
    List<Supplierperformance> findBySupplierId(Integer supplierId);
}

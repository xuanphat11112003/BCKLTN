package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Detailsexportordercost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailExportOrderCostRepository extends JpaRepository<Detailsexportordercost,Long> {
}

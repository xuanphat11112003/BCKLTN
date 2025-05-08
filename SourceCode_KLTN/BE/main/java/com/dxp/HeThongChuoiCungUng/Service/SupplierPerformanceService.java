package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.SupplierPerformanceRequest;
import com.dxp.HeThongChuoiCungUng.Model.Supplierperformance;

import java.util.List;

public interface SupplierPerformanceService {
    public void save(Supplierperformance supplierperformance);
    public List<SupplierPerformanceRequest> getAllSupplier();
    List<Supplierperformance> getPerformanceBySupplierId(Integer supplierId);
}

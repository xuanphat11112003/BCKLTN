package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.SupplierResponse;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    List<Supplier> getAllSupplier(Map<String,String> params);
    List<Supplier> getAllSupplier();
    public void save(Supplier sup);
    Supplier findById (int id);
    List<SupplierResponse> getStats(String id);
    public void Delete(int id);
}

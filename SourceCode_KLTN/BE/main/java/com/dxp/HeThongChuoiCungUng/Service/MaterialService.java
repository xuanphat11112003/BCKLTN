package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;

import java.util.List;
import java.util.Map;

public interface MaterialService {
    Material findById(int id);
    public void save (Material ma);
    List<Material> getALlMaterial(Map<String,String> params);
    List<Material> findBySupplierId(Supplier sup);
    public void Delete(int id);
}

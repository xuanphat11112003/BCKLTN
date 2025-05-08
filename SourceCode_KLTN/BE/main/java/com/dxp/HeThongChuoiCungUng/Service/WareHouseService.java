package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;

import java.util.List;
import java.util.Map;

public interface WareHouseService {
    public Warehouse findById(int id);
    public List<Warehouse> findAll(Map<String,String> params);
    public List<Warehouse> findAll();
    public void save(Warehouse warehouse);
    public Warehouse getWarehouseActive();
    WareHouseRequest findOrderInWareHouse(int id, Map<String, String> params);
    public void Delete(int id);
}

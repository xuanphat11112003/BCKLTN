package com.dxp.HeThongChuoiCungUng.Service;
import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialInventoryRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialStockRequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Material;

import java.util.List;
import java.util.Map;

public interface InventoryService {
    public void save(Inventory inv);
    public List<MaterialInventoryRequest> findAll(Map<String,String> paramns);
    public Map<Integer, MaterialStockRequest> checkMaterialAvailable(List<Manufacturerequest> manufacturerequests);
    public Map<Integer, MaterialStockRequest> checkRemainMaterialAvailable(List<Manufacturerequest> manufacturerequests);
    public List<MaterialInventoryRequest> getInventory(Map<String,String> params, int id);
    public MaterialInventoryRequest findById(int id);
    public Inventory getById( int id);
    public void Delete(int id);
}

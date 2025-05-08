package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.Oderexport;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;

import java.util.List;
import java.util.Map;

public interface OrderExportService {
    public void save(Oderexport oderexport);
    public List<OrderExportRequest> getAllOrderExport(Map<String,String> params);
    OrderExportRequest getById(int id);
    Oderexport findById(int id);
    public List<OrderExportRequest> getOrdersByOrderForAndState(Integer orderForId);
    public void Delete(int id);
}

package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.OrderDetailManufacture;

import java.util.List;

public interface OrderDetailManufactureService {
    public void save(OrderDetailManufacture orderDetailManufacture);
    public List<ProductRequest> getProductProductionData();
}

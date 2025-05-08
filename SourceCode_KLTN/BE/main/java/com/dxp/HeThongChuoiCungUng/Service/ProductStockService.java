package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductStockRequest;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;

import java.util.List;
import java.util.Map;

public interface ProductStockService {
    public void save(Productstock productstock);
    public int checkwareHouse(List<Map<String, Integer>> params);
    public List<ProductStockRequest> checkProductStock(OrderExportRequest orderExportRequest);
    public void updateQuantity(OrderExportRequest orderExportRequest);
    public void delete(Productstock productstock);
    public List<Productstock> findAll();
}

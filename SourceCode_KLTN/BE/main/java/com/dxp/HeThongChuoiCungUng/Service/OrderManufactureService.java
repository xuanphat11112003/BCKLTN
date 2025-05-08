package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderManufactureRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.OrderManufacture;

import java.util.List;
import java.util.Map;

public interface OrderManufactureService {
    public List<OrderManufactureRequest> findAll(Map<String,String> params);
    public void save(OrderManufacture orderManufacture);
    public OrderManufactureRequest findById(int id);
    public OrderManufacture getById(int id);
    public void upDateTransaction (int id, String trans);
    public void delete(int id);

}

package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAllProduct(Map<String,String> params);
    Product findById(int id);
    Product SaveProduct(Product p);
    List<Product> getAllProduct();
    List<ProductRequest> getProductResponse(Map<String, String> params);
    public void Delete(int id);
    List<Product> findAll(Map<String, String> params);
}

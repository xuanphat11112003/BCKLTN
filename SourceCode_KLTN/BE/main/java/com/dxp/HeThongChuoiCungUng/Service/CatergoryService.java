package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Category;

import java.util.List;
import java.util.Map;

public interface CatergoryService {
    List<Category> findByType(String type);
    public void save(Category cat);
    List<Category> getAllCategory(Map<String, String> params);
    public Category findById(int id);
    public void Delete(int id);
}

package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Cost;

import java.util.List;

public interface CostService {
    public void save(Cost cost);
    public List<Cost> findByAll();
    public List<Cost> findByType(String type);
    public Cost findById(int id);
    public void Delete(int id);
}

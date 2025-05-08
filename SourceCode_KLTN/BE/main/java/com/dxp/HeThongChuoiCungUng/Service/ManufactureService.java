package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.Model.Manufacture;

import java.util.List;
import java.util.Map;

public interface ManufactureService {
    public void save(Manufacture manufacture);
    public Manufacture findById(int id);
    public void delete(int id);
    public List<Manufacturerequest> getAllManu(Map<String,String> params);
    public List<Manufacturerequest> getAllManu();
    public void Delete(int id);
}

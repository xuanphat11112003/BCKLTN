package com.dxp.HeThongChuoiCungUng.Service;
import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.Model.Materialstock;

import java.util.List;

public interface MaterialStockService {
    public void save(Materialstock material);
    public void updateQuantity(List<Manufacturerequest> manufacturerequests);
    public void delete (Materialstock materialstock);
    public void updataActice(List<Manufacturerequest> manufacturerequests);
    public void restoreQuantity(List<Manufacturerequest> manufacturerequests);
    public Materialstock findById(int id);
}

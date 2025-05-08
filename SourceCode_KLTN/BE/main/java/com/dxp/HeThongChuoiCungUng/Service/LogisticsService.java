package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Logistics;

public interface LogisticsService {
    public void save(Logistics logistics);
    public void updateEntryDate(int oid, int wid);
    public void updateExitDate(int oid , int wid);
}

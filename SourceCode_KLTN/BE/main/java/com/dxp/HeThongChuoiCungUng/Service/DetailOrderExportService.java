package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Detailexportorder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DetailOrderExportService {
    public void save(Detailexportorder detailexportorder);
    public List<Map<String, Object>> getSalesDataByProduct();
    public List<Map<String, Object>> getSalesSalesHistoryByProduct();
}

package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.Model.Importorder;

import java.util.List;
import java.util.Map;

public interface ImportOrderService {
    Importorder saveOrder(Importorder imp);
//    public Map<Integer, List<Object[]>> getGroupedOrderDetails(Map<String,String> params);
    public List<ImportorderCreatetionRequest> findImportOrder(Map<String,String> params);
    public void upDateActive(int id);
    public ImportorderCreatetionRequest findById(int id);
    public Importorder getById(int id);
    public void Delete(int id);
}

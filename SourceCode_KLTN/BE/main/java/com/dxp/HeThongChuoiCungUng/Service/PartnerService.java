package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.PartnerRequest;
import com.dxp.HeThongChuoiCungUng.Model.Partner;
import com.dxp.HeThongChuoiCungUng.Model.partnerAgency;

import java.util.List;
import java.util.Map;


public interface PartnerService {
    public void save(Partner partner);
    public List<PartnerRequest> getAll(Map<String,String> params);
    Partner findById(int id);
    public void addContract(Partner partner, int id);
    public void Delete(int id);
}

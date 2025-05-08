package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Transportpartner;

import java.util.List;

public interface TransportPartnerService {
    public Transportpartner findById(int id);
    public List<Transportpartner> findAll();
}

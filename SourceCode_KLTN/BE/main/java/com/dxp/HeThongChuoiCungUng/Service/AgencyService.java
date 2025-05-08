package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.Model.Agency;

import java.util.List;

public interface AgencyService {
    public void save(Agency agency);
    public Agency findById(int id);
    public List<Agency> findAll();
    public void Delete( int id );
    public Agency findByName(String name);
//    public List<Agency> findActiveTrue();
}

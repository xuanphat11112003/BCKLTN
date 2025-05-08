package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Transportpartner;
import com.dxp.HeThongChuoiCungUng.Repository.TransportPartnerRepository;
import com.dxp.HeThongChuoiCungUng.Service.TransportPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportPartnerServiceImpl implements TransportPartnerService {
    @Autowired
    private TransportPartnerRepository transportPartnerRepository;

    @Override
    public Transportpartner findById(int id) {
        return this.transportPartnerRepository.findById(id);
    }

    @Override
    public List<Transportpartner> findAll() {
        return this.transportPartnerRepository.findAll();
    }
}

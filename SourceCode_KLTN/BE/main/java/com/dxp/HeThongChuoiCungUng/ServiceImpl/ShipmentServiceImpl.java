package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Shipment;
import com.dxp.HeThongChuoiCungUng.Repository.ShipmentRepository;
import com.dxp.HeThongChuoiCungUng.Service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public void save(Shipment shipment) {
        this.shipmentRepository.save(shipment);
    }
}

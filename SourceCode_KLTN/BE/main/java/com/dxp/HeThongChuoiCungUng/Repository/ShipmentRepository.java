package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}

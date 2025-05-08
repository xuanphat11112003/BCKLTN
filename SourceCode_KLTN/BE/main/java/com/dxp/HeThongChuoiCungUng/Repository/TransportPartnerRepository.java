package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Transportpartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportPartnerRepository extends JpaRepository<Transportpartner,Long> {
    Transportpartner findById(int id);
}

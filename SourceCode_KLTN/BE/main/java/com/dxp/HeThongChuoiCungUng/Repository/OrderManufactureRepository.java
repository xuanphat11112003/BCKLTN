package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.OrderManufacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManufactureRepository extends JpaRepository<OrderManufacture,Long>, JpaSpecificationExecutor<OrderManufacture> {
    OrderManufacture findById(int id);
}

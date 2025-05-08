package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Long>, JpaSpecificationExecutor<Material> {
    Material findById(int id);
    List<Material> findBySupplierId(Supplier sup);
}

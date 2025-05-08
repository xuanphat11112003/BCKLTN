package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Long> {
    Agency findById(int id);
    Agency findByUserAID_Id(int userId);
//    @Query("SELECT a FROM Agency a WHERE a.active = true")
//    List<Agency> findActiveAgencies();
}

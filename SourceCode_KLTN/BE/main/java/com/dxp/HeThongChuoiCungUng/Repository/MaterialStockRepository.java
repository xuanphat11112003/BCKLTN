package com.dxp.HeThongChuoiCungUng.Repository;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Materialstock;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialStockRepository extends JpaRepository<Materialstock,Long> {
    @Query("SELECT ms FROM Materialstock ms WHERE ms.materialId.id = ?1 ORDER BY ms.inventoryId.entryDate ASC")
    List<Materialstock> findByIdOrderByInventoryEntryDateAsc(int id);
    @Query("SELECT COUNT(ms) FROM Materialstock ms WHERE ms.inventoryId = :inventory")
    Long countByInventory(@Param("inventory") Inventory inventory);
    Materialstock findById(int id);
}

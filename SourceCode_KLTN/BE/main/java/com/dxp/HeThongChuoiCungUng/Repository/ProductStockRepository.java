package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockRepository extends JpaRepository<Productstock,Long> {
    public Productstock findById(int id);
    List<Productstock> findByProductId(Product productId);
    @Query("SELECT ps FROM Productstock ps WHERE ps.productId.id = ?1 ORDER BY ps.inventoryId.entryDate ASC")
    List<Productstock> findByIdOrderByInventoryEntryDateAsc(int id);
    @Query("SELECT COUNT(ps) FROM Productstock ps WHERE ps.inventoryId = :inventory")
    Long countByInventory(@Param("inventory") Inventory inventory);

}

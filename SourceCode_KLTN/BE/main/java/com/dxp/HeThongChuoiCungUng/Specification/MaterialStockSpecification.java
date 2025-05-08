package com.dxp.HeThongChuoiCungUng.Specification;//package com.dxp.HeThongChuoiCungUng.Specification;
//
//import com.dxp.HeThongChuoiCungUng.Model.Inventory;
//import com.dxp.HeThongChuoiCungUng.Model.Materialstock;
//import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
//import jakarta.persistence.criteria.Join;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.Map;
//
//public class MaterialStockSpecification {
//    public static Specification<Materialstock> getAllMaterialStock(Map<String,String> params){
//        return ((root, query, criteriaBuilder) -> {
//            Join<Materialstock, Inventory> inventoryJoin = root.join("inventoryId");
//            Join<Inventory, Warehouse> warehouseJoin =
//        });
//    }
//}

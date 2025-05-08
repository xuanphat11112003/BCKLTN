package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailImportOrderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialInventoryRequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Materialstock;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Service.*;
import com.dxp.HeThongChuoiCungUng.Utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MaterialStockApiController {
    @Autowired
    private ImportOrderService importOrderService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialStockService materialStockService;

    @PostMapping("/material-stock/{orderId}")
    public ResponseEntity<?> materialStock(@RequestBody MaterialInventoryRequest materialInventoryRequest,
                                           @PathVariable (value = "orderId") int orderId){
        try{
            this.importOrderService.upDateActive(orderId);
            Inventory inventory = new Inventory();
            inventory.setEntryDate(materialInventoryRequest.getEntryDate());
            Warehouse warehouse = this.wareHouseService.findById(materialInventoryRequest.getWareHouseId());
            inventory.setWarehouse(warehouse);
            Map<String,String> params = new HashMap<>();
            String id = String.valueOf(orderId);
            this.inventoryService.save(inventory);
            params.put("orderId",id);
            List<ImportorderCreatetionRequest> importorderCreatetionRequests = this.importOrderService.findImportOrder(params);
            for(DetailImportOrderCreatetionRequest imp : importorderCreatetionRequests.get(0).getDetail()){
                Materialstock materialstock = new Materialstock();
                materialstock.setInventoryId(inventory);
                Material material = this.materialService.findById(imp.getMaterialID());
                materialstock.setMaterialId(material);
                int amount = material.getWeight() * imp.getQuantity();
                materialstock.setAmount(amount);
                materialstock.setActive(true);
                this.materialStockService.save(materialstock);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }
    @PostMapping("/updateExpire")
    public ResponseEntity<?> updateExpireDate(@RequestBody List<Map<String,Object>> params) throws ParseException {
        for (Map<String,Object> map : params){
            Materialstock materialstock = this.materialStockService.findById(Integer.parseInt(map.get("id").toString()));
            Date date = StringUtils.getDateFormating().parse((String) map.get("date_expire"));
            materialstock.setDateExpiry(date);
            this.materialStockService.save(materialstock);
        }
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }
}

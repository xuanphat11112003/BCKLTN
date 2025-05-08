package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.InventoryService;
import com.dxp.HeThongChuoiCungUng.Service.OrderManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProductStockApiController {
    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private OrderManufactureService orderManufactureService;
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("productStock/{orderId}")
    public ResponseEntity<?> addProductStock (@PathVariable(value = "orderId") int id){
        try{
            OrderManufacture orderManufacture = this.orderManufactureService.getById(id);
            this.orderManufactureService.upDateTransaction(id,"Đã hoàn tất");
            Inventory inventory = new Inventory();
            inventory.setWarehouse(orderManufacture.getWarehouseID());
            Date date = new Date();
            inventory.setEntryDate(date);
            this.inventoryService.save(inventory);
            for(OrderDetailManufacture orderDetailManufacture : orderManufacture.getOrderDetailManufactureCollection()){
                Productstock productstock = new Productstock();
                productstock.setActive(true);
                productstock.setProductId(orderDetailManufacture.getProduct());
                productstock.setAmount(orderDetailManufacture.getQuantity());
                productstock.setInventoryId(inventory);
                this.productStockService.save(productstock);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}

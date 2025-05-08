package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderManufactureRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.InventoryService;
import com.dxp.HeThongChuoiCungUng.Service.OrderManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.ProductStockService;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderManufactureApiController {
    @Autowired
    UserService userService;
    @Autowired
    OrderManufactureService orderManufactureService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductStockService productStockService;


    @GetMapping("api/orderManu")
    @CrossOrigin
    public ResponseEntity<?> getOrderManufacture(Principal principal){
        User user = this.userService.LoadUserByName(principal.getName());
        Employee employee = this.userService.getEmployeeByUserId(user.getId());
        Map<String, String> params = new HashMap<>();
        params.put("warehouseName", employee.getPosition().getName());
        List<OrderManufactureRequest> orderManufactureRequest = this.orderManufactureService.findAll(params);
        return ResponseEntity.ok(orderManufactureRequest);
    }

    @PutMapping("api/orderManufacture/{orderId}")
    @CrossOrigin
    public ResponseEntity<?> handleOrderManufacture(@PathVariable(value = "orderId") int id){
        this.orderManufactureService.upDateTransaction(id,"Đang thực hiện");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("api/productStock/{orderId}")
    @CrossOrigin
    public ResponseEntity<?> completeOrderManufacture (@PathVariable(value = "orderId") int id){
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

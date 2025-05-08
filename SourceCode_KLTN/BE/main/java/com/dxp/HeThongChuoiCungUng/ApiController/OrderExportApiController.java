package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailOrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductStockRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class OrderExportApiController {
    @Autowired
    private OrderExportService orderExportService;
    @Autowired
    private DetailOrderExportService detailOrderExportService;
    @Autowired
    private UserService userService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CostService costService;
    @Autowired
    private DetailExportOrderCostService detailExportOrderCostService;
    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private TransportPartnerService transportPartnerService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("orderExport")
    public ResponseEntity<?> addOrderExport(@RequestBody OrderExportRequest orderExportRequest, Principal principal){
        Oderexport oderexport = new Oderexport();
        Date date = new Date();
        try {
            oderexport.setOrder_by(this.userService.LoadUserByName(principal.getName()));
            oderexport.setOrder_for(this.agencyService.findById(orderExportRequest.getOrderForId()));

            oderexport.setCreatedDate(date);
            oderexport.setState(Oderexport.State.V1.getValue());
            oderexport.setTotalPrice(orderExportRequest.getTotalPrice());
            oderexport.setTypeBuy("tiền mặt");
            this.orderExportService.save(oderexport);
            for(DetailOrderExportRequest detailOrderExportRequest : orderExportRequest.getDetails()){
                Detailexportorder detailexportorder = new Detailexportorder();
                detailexportorder.setExportOrderId(oderexport);
                detailexportorder.setAmount(detailOrderExportRequest.getAmount());
                detailexportorder.setDiscount(0);
                detailexportorder.setTotalPrice(detailOrderExportRequest.getTotal_price());
                detailexportorder.setMaterial(this.productService.findById(detailOrderExportRequest.getProductId()));
                this.detailOrderExportService.save(detailexportorder);

            }
            Cost cost = new Cost();
            Detailsexportordercost detailsexportordercost = new Detailsexportordercost();
            cost = costService.findByType("wareHouse").get(0);
            detailsexportordercost.setCost(cost);
            detailsexportordercost.setCreateDate(date);
            detailsexportordercost.setExportorderId(oderexport);
            int wareCost = orderExportRequest.getWareHousePrice() / cost.getPrice();
            detailsexportordercost.setDescription(wareCost);
            this.detailExportOrderCostService.save(detailsexportordercost);
            Detailsexportordercost detailsexportordercost1 = new Detailsexportordercost();
            cost = this.costService.findById(orderExportRequest.getTransId());
            detailsexportordercost1.setCost(cost);
            detailsexportordercost1.setExportorderId(oderexport);
            detailsexportordercost1.setCreateDate(date);
            detailsexportordercost1.setDescription(orderExportRequest.getTransPrice()/cost.getPrice());
            this.detailExportOrderCostService.save(detailsexportordercost1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("orderExport/{id}/{tid}")
    public ResponseEntity<?> transOrder(@PathVariable(value = "id") int id, @PathVariable(value = "tid")int tid){
        OrderExportRequest orderExportRequest = this.orderExportService.getById(id);
        List<ProductStockRequest> productStockRequests = this.productStockService.checkProductStock(orderExportRequest);
        if (productStockRequests != null){
            return new ResponseEntity<>(productStockRequests,HttpStatus.BAD_REQUEST);
        }
        Shipment shipment = new Shipment();
        Oderexport oderexport = this.orderExportService.findById(id);
        oderexport.setState(Oderexport.State.V2.getValue());
        shipment.setPrice(orderExportRequest.getTransPrice());
        shipment.setOderid(oderexport);
        shipment.setExpressId(this.transportPartnerService.findById(tid));
        this.shipmentService.save(shipment);
        Logistics logistics = new Logistics();
        Warehouse warehouse = this.wareHouseService.getWarehouseActive();
        logistics.setWarehouse(warehouse);
        logistics.setType(Logistics.Type.coming);
        logistics.setLogistcs(oderexport);
        this.logisticsService.save(logistics);
        this.productStockService.updateQuantity(orderExportRequest);
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }

    @PostMapping("api/orderExport")
    @CrossOrigin
    public ResponseEntity<?> addOrderExportFromAgency(@RequestBody OrderExportRequest orderExportRequest, Principal principal){
        Oderexport oderexport = new Oderexport();
        Date date = new Date();
        try {
            oderexport.setOrder_by(this.userService.LoadUserByName("xuanphat"));
            oderexport.setOrder_for(this.agencyService.findByName(principal.getName()));

            oderexport.setCreatedDate(date);
            oderexport.setState(Oderexport.State.V1.getValue());
            oderexport.setTotalPrice(orderExportRequest.getTotalPrice());
            oderexport.setTypeBuy("chuyển khoản");
            this.orderExportService.save(oderexport);
            for(DetailOrderExportRequest detailOrderExportRequest : orderExportRequest.getDetails()){
                Detailexportorder detailexportorder = new Detailexportorder();
                detailexportorder.setExportOrderId(oderexport);
                detailexportorder.setAmount(detailOrderExportRequest.getAmount());
                detailexportorder.setDiscount(0);
                detailexportorder.setTotalPrice(detailOrderExportRequest.getTotal_price());
                detailexportorder.setMaterial(this.productService.findById(detailOrderExportRequest.getProductId()));
                this.detailOrderExportService.save(detailexportorder);

            }
            Cost cost = new Cost();
            Detailsexportordercost detailsexportordercost = new Detailsexportordercost();
            cost = costService.findByType("wareHouse").get(0);
            detailsexportordercost.setCost(cost);
            detailsexportordercost.setCreateDate(date);
            detailsexportordercost.setExportorderId(oderexport);
            int wareCost = orderExportRequest.getWareHousePrice() / cost.getPrice();
            detailsexportordercost.setDescription(wareCost);
            this.detailExportOrderCostService.save(detailsexportordercost);
            Detailsexportordercost detailsexportordercost1 = new Detailsexportordercost();
            cost = this.costService.findById(orderExportRequest.getTransId());
            detailsexportordercost1.setCost(cost);
            detailsexportordercost1.setExportorderId(oderexport);
            detailsexportordercost1.setCreateDate(date);
            detailsexportordercost1.setDescription(orderExportRequest.getTransPrice()/cost.getPrice());
            this.detailExportOrderCostService.save(detailsexportordercost1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("api/orderExport")
    @CrossOrigin
    public ResponseEntity<?> getOrderExport(Principal principal){
        Agency agency = this.agencyService.findByName(principal.getName());
        List<OrderExportRequest> orderExportRequestList = this.orderExportService.getOrdersByOrderForAndState(agency.getId());
        return ResponseEntity.ok(orderExportRequestList);
    }

    @PutMapping("api/orderExport/{orderID}")
    @CrossOrigin
    public ResponseEntity<?> cancelOrder(@PathVariable(value = "orderID") int id){
        Oderexport oderexport = this.orderExportService.findById(id);
        oderexport.setState(Oderexport.State.V4.getValue());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("api/orderOfWareHouse")
    @CrossOrigin
    public ResponseEntity<?> getOrder(Principal principal, @RequestParam Map<String, String> params){
        User user = this.userService.LoadUserByName(principal.getName());
        Employee employee = this.userService.getEmployeeByUserId(user.getId());
        int idWareHouse = employee.getPosition().getId();
        WareHouseRequest wareHouseRequests = this.wareHouseService.findOrderInWareHouse(idWareHouse, params);
        return ResponseEntity.ok(wareHouseRequests);
    }

    @DeleteMapping("orderExport/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(@PathVariable (value = "id") int id){
        this.orderExportService.Delete(id);
    }

}

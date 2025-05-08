package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.Logistics;
import com.dxp.HeThongChuoiCungUng.Model.Oderexport;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Service.LogisticsService;
import com.dxp.HeThongChuoiCungUng.Service.OrderExportService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WareHouseApiController {
    @Autowired
    private OrderExportService orderExportService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private LogisticsService logisticsService;


    @PutMapping("/updateLogictis/{oid}/{wid}")
    public String updateLogistics (@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid){
        this.logisticsService.updateEntryDate(oid,wid);
        return "Đã nhận đơn hàng";
    }
    @PostMapping("/updateLogictis/{oid}/{wid}")
    public String updateExitLogistics (@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid,
                                       @RequestBody Map<String, Integer> params1){
        this.logisticsService.updateExitDate(oid,wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        Logistics logistics = new Logistics();
        logistics.setType(Logistics.Type.coming);
        logistics.setLogistcs(oderexport);
        Warehouse warehouse = this.wareHouseService.findById(params1.get("warehouse"));
        logistics.setWarehouse(warehouse);
        this.logisticsService.save(logistics);
        return "Đã chuyển đơn hàng thành công";
    }

    @PutMapping("/completeOrder/{oid}/{wid}")
    public String completeOrder(@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid){
        this.logisticsService.updateExitDate(oid,wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        oderexport.setState(Oderexport.State.V5.getValue());
        this.orderExportService.save(oderexport);
        return "Đơn hàng chuyển cho khách hàng";
    }

    @PutMapping("api/updateLogictis/{oid}/{wid}")
    @CrossOrigin
    public String updateLogisticsForApi (@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid){
        this.logisticsService.updateEntryDate(oid,wid);
        return "Đã nhận đơn hàng";
    }
    @PostMapping("api/updateLogictis/{oid}/{wid}")
    @CrossOrigin
    public String updateExitLogisticsForApi (@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid,
                                       @RequestBody Map<String, Integer> params1){
        this.logisticsService.updateExitDate(oid,wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        Logistics logistics = new Logistics();
        logistics.setType(Logistics.Type.coming);
        logistics.setLogistcs(oderexport);
        Warehouse warehouse = this.wareHouseService.findById(params1.get("warehouse"));
        logistics.setWarehouse(warehouse);
        this.logisticsService.save(logistics);
        return "Đã chuyển đơn hàng thành công";
    }
    @GetMapping("api/wareHouse")
    @CrossOrigin
    public ResponseEntity<?> getWareHouse(@RequestParam Map<String,String> params){
        List<Warehouse> warehouses = this.wareHouseService.findAll(params);
        List<WareHouseRequest> wareHouseRequests = new ArrayList<>();
        for(Warehouse w: warehouses){
            WareHouseRequest wareHouseRequest = new WareHouseRequest();
            wareHouseRequest.setName(w.getName());
            wareHouseRequest.setAddress(w.getAddress());
            wareHouseRequest.setId(w.getId());
            wareHouseRequests.add(wareHouseRequest);
        }
        return ResponseEntity.ok(wareHouseRequests);
    }

    @PutMapping("api/completeOrder/{oid}/{wid}")
    @CrossOrigin
    public String completeOrderForApi(@PathVariable(value = "oid") int oid , @PathVariable(value = "wid") int wid){
        this.logisticsService.updateExitDate(oid,wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        oderexport.setState(Oderexport.State.V5.getValue());
        this.orderExportService.save(oderexport);
        return "Đơn hàng chuyển cho khách hàng";
    }
    @DeleteMapping("warehouse/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(@PathVariable (value = "id") int id){
        this.wareHouseService.Delete(id);
    }
}

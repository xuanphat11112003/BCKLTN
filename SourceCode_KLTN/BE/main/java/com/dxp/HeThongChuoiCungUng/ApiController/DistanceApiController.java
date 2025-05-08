package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.Model.Cost;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import com.dxp.HeThongChuoiCungUng.Service.CostService;
import com.dxp.HeThongChuoiCungUng.Service.DistanceService;
import com.dxp.HeThongChuoiCungUng.Service.ProductStockService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DistanceApiController {
    @Autowired
    private DistanceService distanceService;
    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private CostService costService;
    @PostMapping("/api/calculateDistance")
    public Map<String, Integer> calculateDistance(@RequestBody Map<String, Object> params) throws MalformedURLException {
        String defaultAddress = this.wareHouseService.getWarehouseActive().getAddress();
        String userAddress = params.get("userAddress").toString();
        double far = distanceService.calculateDistance(defaultAddress, userAddress);
        int km = (int) Math.round(far);
        Map<String, Integer> response = new HashMap<>();
        int ware = this.productStockService.checkwareHouse((List<Map<String, Integer>>) params.get("details"));
        int cost = this.costService.findByType("Warehouse").get(0).getPrice();
        response.put("trans", km);
        response.put("warehouseCost",ware * cost);
        return response;
    }
    @PostMapping("/api/Cost")
    @CrossOrigin
    public Map<String, Integer> calculateDistance1(@RequestBody Map<String, Object> params) throws MalformedURLException {
        String defaultAddress = this.wareHouseService.getWarehouseActive().getAddress();
        String userAddress = params.get("userAddress").toString();
        double far = distanceService.calculateDistance(defaultAddress, userAddress);
        int km = (int) Math.round(far);
        Map<String, Integer> response = new HashMap<>();
        int ware = this.productStockService.checkwareHouse((List<Map<String, Integer>>) params.get("details"));
        int cost = this.costService.findByType("Warehouse").get(0).getPrice();
        List<Cost> costs = this.costService.findByType("Transport");
        response.put("trans", km);
        response.put("warehouseCost",ware * cost);
        response.put("cost1", costs.get(0).getPrice());
        response.put("cost2", costs.get(1).getPrice());
        return response;
    }
    @DeleteMapping("cost/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCost(@PathVariable (value = "id") int id){
        this.costService.Delete(id);
    }

    @GetMapping("/api/getCoordinates")
    @CrossOrigin
    public double[][] getCoordinate(@RequestParam String orderAddress, @RequestParam String customerAddress) {
        double[] orderCoordinates = distanceService.getCoordinates(orderAddress);
        double[] customerCoordinates = distanceService.getCoordinates(customerAddress);

        return new double[][] { orderCoordinates, customerCoordinates };
    }

}

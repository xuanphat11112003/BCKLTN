package com.dxp.HeThongChuoiCungUng.ApiController;
import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductStockRequest;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import com.dxp.HeThongChuoiCungUng.Service.DetailOrderExportService;
import com.dxp.HeThongChuoiCungUng.Service.ManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DetailExportAPIController {
    @Autowired
    private DetailOrderExportService detailOrderExportService;
    @Autowired
    private ProductStockService productStockService;
    @Autowired
    private ManufactureService manufactureService;

    @GetMapping("/api/sales-history")
    @CrossOrigin
    public List<Map<String, Object>> getSalesHistory() {
        return detailOrderExportService.getSalesDataByProduct();
    }
    @PostMapping("/product-calculation")
    public List<Manufacturerequest> productCalculation(@RequestBody List<Map<String, Object>> params) {
        List<Productstock> productstocks = productStockService.findAll();
        List<ProductStockRequest> productstocks1 = new ArrayList<>();
        List<Manufacturerequest> manufacturerequests = new ArrayList<>();
        for (Map<String, Object> param : params){
            int productId = (int) param.get("product_id");
            double safetyStock = (double) param.get("safety_stock");
            Double nextSale = (Double) param.get("next_sale");
                for( Productstock productStock: productstocks){
                    if(productStock.getId() == productId){
                        int amount = productStock.getAmount();
                        double result = (nextSale + safetyStock) - amount;
                        ProductStockRequest request = new ProductStockRequest();
                        request.setProductId(productId);
                        request.setAmount((int) result);
                        productstocks1.add(request);
                    }else {
                        ProductStockRequest request = new ProductStockRequest();
                        double result = (nextSale + safetyStock);
                        request.setProductId(productId);
                        request.setAmount((int) result);
                        productstocks1.add(request);
                    }
                }
        }
        for (ProductStockRequest pds : productstocks1){
            Map<String,String> p = new HashMap<>();
            p.put("productId", String.valueOf(pds.getProductId()));
            List<Manufacturerequest> manu = manufactureService.getAllManu(p);
            if(manu.isEmpty()){
                continue;
            }
            for(Manufacturerequest manus : manu){
                Manufacturerequest manuDTO = new Manufacturerequest();
                manuDTO.setProductId(manus.getProductId());
                manuDTO.setAmount(manus.getAmount()*pds.getAmount());
                manuDTO.setMaterialId(manus.getMaterialId());
                manuDTO.setProductName(manus.getProductName());
                manuDTO.setMaterialName(manus.getMaterialName());
                manuDTO.setWeight(pds.getAmount());
                manuDTO.setAgencyMaterial(manus.getAgencyMaterial());
                manuDTO.setUnit(manus.getUnit());
                manuDTO.setPrice(manus.getPrice());
                manufacturerequests.add(manuDTO);

            }
        }
        return manufacturerequests;

    }
}

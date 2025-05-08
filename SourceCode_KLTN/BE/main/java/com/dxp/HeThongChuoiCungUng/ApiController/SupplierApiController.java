package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.SupplierPerformanceRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.ImportOrderService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierPerformanceService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
public class SupplierApiController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierPerformanceService supplierPerformanceService;
    @Autowired
    private ImportOrderService importOrderService;

    @PostMapping("/supplier/evaluate")
    public ResponseEntity<?> Evaluate(@RequestBody SupplierPerformanceRequest supplierPerformanceRequest,
                                      Principal principal){
        try{
            Supplierperformance supplierperformance = new Supplierperformance();
            supplierperformance.setQualityRating(supplierPerformanceRequest.getQualityRating());
            Date date = new Date();
            supplierperformance.setEvaluationDate(date);
            supplierperformance.setDeliveryRating(supplierPerformanceRequest.getDeliveryRating());
            supplierperformance.setComment(supplierPerformanceRequest.getComment());
            supplierperformance.setPriceRating(supplierPerformanceRequest.getPriceRating());
            Supplier sup = this.supplierService.findById(supplierPerformanceRequest.getSupplierId());
            supplierperformance.setSupplier(sup);
            User user = this.userService.LoadUserByName(principal.getName());
//            Manager manager = user.getManager();
//            supplierperformance.setManagerId(manager);
            Importorder importorder = this.importOrderService.getById(supplierPerformanceRequest.getOrderId());
            importorder.setActiveEvaluate(true);
            this.importOrderService.saveOrder(importorder);
            supplierperformance.setImportorder(importorder);
            this.supplierPerformanceService.save(supplierperformance);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(supplierPerformanceRequest, HttpStatus.CREATED);

    }
    @DeleteMapping("supplier/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable (value = "id") int id){
        this.supplierService.Delete(id);
    }
}

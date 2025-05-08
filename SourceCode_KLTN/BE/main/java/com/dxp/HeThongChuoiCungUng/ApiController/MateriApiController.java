package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialRequest;
import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;
import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MateriApiController {
    @Autowired
    MaterialService maService;
    @Autowired
    SupplierService supService;

    @GetMapping("materialBySupplier/{id}")
    public ResponseEntity<?> getMaterialBySupplier(@PathVariable(value = "id") int id){
        Supplier sup = this.supService.findById(id);
        List<Material> materials = this.maService.findBySupplierId(sup);
        List<MaterialRequest> materialRequests = new ArrayList<>();
        for(Material ma : materials){
            materialRequests.add(new MaterialRequest(ma));
        }

        return new ResponseEntity<>(materialRequests, HttpStatus.OK);
    }
    @DeleteMapping("material/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(@PathVariable (value = "id") int id){
        this.maService.Delete(id);
    }
}

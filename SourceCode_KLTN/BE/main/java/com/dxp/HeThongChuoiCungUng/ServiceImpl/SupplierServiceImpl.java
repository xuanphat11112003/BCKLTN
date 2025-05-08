package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.SupplierResponse;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;
import com.dxp.HeThongChuoiCungUng.Model.Supplierperformance;
import com.dxp.HeThongChuoiCungUng.Repository.SupplierRepository;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import com.dxp.HeThongChuoiCungUng.Specification.SupplierSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supRePo;

    @Override
    public List<Supplier> getAllSupplier(Map<String, String> params) {
        Specification<Supplier> specification = SupplierSpecification.Search(params);
        return this.supRePo.findAll(specification);
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return this.supRePo.findAll();
    }

    @Override
    public void save(Supplier sup) {
        this.supRePo.save(sup);
    }

    @Override
    public Supplier findById(int id) {
        return this.supRePo.findById(id);
    }

    @Override
    public List<SupplierResponse> getStats(String id) {
        List<Supplier> suppliers = new ArrayList<>();
        if(id != null){
            Map<String, String> params = new HashMap<>();
            params.put("name", id);
            suppliers = this.getAllSupplier(params);
        }else {
            suppliers = this.getAllSupplier();
        }
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        for (Supplier supplier : suppliers){
            SupplierResponse supplierResponse = new SupplierResponse();
            supplierResponse.setId(supplier.getId());
            supplierResponse.setName(supplier.getName());
            supplierResponse.setDeliveryRatings(supplier.getSupplierperformanceCollection().stream()
                    .mapToInt(Supplierperformance::getDeliveryRating).average().orElse(0));
            supplierResponse.setPriceRatings(supplier.getSupplierperformanceCollection().stream()
                    .mapToInt(Supplierperformance::getPriceRating).average().orElse(0));
            supplierResponse.setQualityRatings(supplier.getSupplierperformanceCollection().stream()
                    .mapToInt(Supplierperformance::getQualityRating).average().orElse(0));
            supplierResponses.add(supplierResponse);
        }
        return supplierResponses;
    }

    @Override
    public void Delete(int id) {
        this.supRePo.delete(this.findById(id));
    }
}

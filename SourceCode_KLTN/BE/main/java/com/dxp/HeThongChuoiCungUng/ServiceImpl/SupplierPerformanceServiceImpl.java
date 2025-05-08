package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.SupplierPerformanceRequest;
import com.dxp.HeThongChuoiCungUng.Model.Supplierperformance;
import com.dxp.HeThongChuoiCungUng.Repository.SupplierPerformanceRepository;
import com.dxp.HeThongChuoiCungUng.Service.SupplierPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierPerformanceServiceImpl implements SupplierPerformanceService {
    @Autowired
    private SupplierPerformanceRepository supplierPerformanceRepository;

    @Override
    public void save(Supplierperformance supplierperformance) {
        this.supplierPerformanceRepository.save(supplierperformance);
    }

    @Override
    public List<SupplierPerformanceRequest> getAllSupplier() {
        List<Supplierperformance> supplierperformances = this.supplierPerformanceRepository.findAll();
        List<SupplierPerformanceRequest> supplierPerformanceRequests = new ArrayList<>();
        for(Supplierperformance sup : supplierperformances){
            SupplierPerformanceRequest supRequest = new SupplierPerformanceRequest();
            supRequest.setSupplierId(sup.getSupplier().getId());
            supRequest.setSupplierName(sup.getSupplier().getName());
            supRequest.setId(sup.getId());
            supRequest.setComment(sup.getComment());
            supRequest.setEvaluateDate(sup.getEvaluationDate());
            supRequest.setDeliveryRating(sup.getDeliveryRating());
            supRequest.setPriceRating(sup.getPriceRating());
            supRequest.setUserId(sup.getManagerId().getUserId());
            supRequest.setQualityRating(sup.getQualityRating());
            supRequest.setUserName(sup.getManagerId().getUser().getUsername());
        }
        return supplierPerformanceRequests;
    }

    @Override
    public List<Supplierperformance> getPerformanceBySupplierId(Integer supplierId) {
        return supplierPerformanceRepository.findBySupplierId(supplierId);
    }
}

package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Detailsexportordercost;
import com.dxp.HeThongChuoiCungUng.Repository.DetailExportOrderCostRepository;
import com.dxp.HeThongChuoiCungUng.Service.DetailExportOrderCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailExportOrderCostServiceImpl implements DetailExportOrderCostService {
    @Autowired
    private DetailExportOrderCostRepository detailExportOrderCostRepository;
    @Override
    public void save(Detailsexportordercost detailsexportordercost) {
        this.detailExportOrderCostRepository.save(detailsexportordercost);
    }
}

package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Detailimportorder;
import com.dxp.HeThongChuoiCungUng.Repository.DetailImportOrderRepository;
import com.dxp.HeThongChuoiCungUng.Service.DetailImportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailImportOrderServiceImpl implements DetailImportOrderService {
    @Autowired
    DetailImportOrderRepository detailImportOrderRepository;
    @Override
    public void save(Detailimportorder detail) {
        this.detailImportOrderRepository.save(detail);

    }
}

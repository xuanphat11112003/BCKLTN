package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Logistics;
import com.dxp.HeThongChuoiCungUng.Model.Oderexport;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Repository.LogisticsRepository;
import com.dxp.HeThongChuoiCungUng.Service.LogisticsService;
import com.dxp.HeThongChuoiCungUng.Service.OrderExportService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    private LogisticsRepository logisticsRepository;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private OrderExportService orderExportService;
    @Override
    public void save(Logistics logistics) {
        this.logisticsRepository.save(logistics);
    }

    @Override
    public void updateEntryDate(int oid, int wid) {
        Warehouse warehouse = this.wareHouseService.findById(wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        for(Logistics logistics: warehouse.getLogisticsCollection()){
            if(logistics.getLogistcs().equals(oderexport)){
                if(logistics.getEntryDate() == null){
                    Date date = new Date();
                    logistics.setEntryDate(date);
                    logistics.setType(Logistics.Type.arrived);
                    this.save(logistics);
                }
            }
        }
    }

    @Override
    public void updateExitDate(int oid, int wid) {
        Warehouse warehouse = this.wareHouseService.findById(wid);
        Oderexport oderexport = this.orderExportService.findById(oid);
        for(Logistics logistics: warehouse.getLogisticsCollection()){
            if(logistics.getLogistcs().equals(oderexport)){
                if(logistics.getExitDate() == null){
                    Date date = new Date();
                    logistics.setExitDate(date);
                    logistics.setType(Logistics.Type.has_left);
                    this.save(logistics);
                }
            }
        }
    }
}

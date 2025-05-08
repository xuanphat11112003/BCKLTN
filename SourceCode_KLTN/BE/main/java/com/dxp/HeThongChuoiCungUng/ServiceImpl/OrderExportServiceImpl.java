package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailOrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.LogicticsRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Repository.OrderExportRepository;
import com.dxp.HeThongChuoiCungUng.Repository.WareHouseRepository;
import com.dxp.HeThongChuoiCungUng.Service.OrderExportService;
import com.dxp.HeThongChuoiCungUng.Specification.ExportOrderSpecification;
import com.dxp.HeThongChuoiCungUng.Specification.WareHouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderExportServiceImpl implements OrderExportService {
    @Autowired
    private OrderExportRepository orderExportRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Override
    public void save(Oderexport oderexport) {
        this.orderExportRepository.save(oderexport);
    }

    @Override
    public List<OrderExportRequest> getAllOrderExport(Map<String, String> params) {
        Specification<Oderexport> specification = ExportOrderSpecification.JoinExportOrder(params);
        List<Oderexport> oderexports = this.orderExportRepository.findAll(specification);
        return oderexports.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderExportRequest getById(int id) {
        return this.convertToDTO(this.orderExportRepository.findById(id));
    }

    @Override
    public Oderexport findById(int id) {
        return this.orderExportRepository.findById(id);
    }

    @Override
    public List<OrderExportRequest> getOrdersByOrderForAndState(Integer orderForId) {
        List<Oderexport> oderexports = this.orderExportRepository.findOrdersByOrderForAndState(orderForId);
        return oderexports.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void Delete(int id) {
        Oderexport oderexport = this.findById(id);
        this.orderExportRepository.delete(oderexport);
    }


    private OrderExportRequest convertToDTO(Oderexport oderexport){
        OrderExportRequest orderExportRequest = new OrderExportRequest();
        orderExportRequest.setId(oderexport.getId());
        orderExportRequest.setOrderById(oderexport.getOrder_by().getId());
        orderExportRequest.setOrderByName(oderexport.getOrder_by().getUsername());
        orderExportRequest.setOrderForId(oderexport.getOrder_for().getId());
        orderExportRequest.setOrderForName(oderexport.getOrder_for().getUser().getUsername());
        orderExportRequest.setState(oderexport.getState());
        orderExportRequest.setTotalPrice(oderexport.getTotalPrice());
        if(oderexport.getShipment() != null){
            orderExportRequest.setTransId(oderexport.getShipment().getExpressId().getId());
            orderExportRequest.setTransName(oderexport.getShipment().getExpressId().getName());
        }
        for(Detailsexportordercost detailsexportordercost : oderexport.getDetailsexportordercostCollection()){
            if(Cost.Type.Warehouse.equals(detailsexportordercost.getCost().getType())){
                int price = detailsexportordercost.getDescription() * detailsexportordercost.getCost().getPrice();
                orderExportRequest.setWareHousePrice(price);
            }else {
                int price = detailsexportordercost.getDescription() * detailsexportordercost.getCost().getPrice();
                orderExportRequest.setTransPrice(price);
            }
        }
        List<DetailOrderExportRequest> detailOrderExportRequest = oderexport.getDetailexportorderCollection()
                .stream().map(this::detailOrderExportRequest).collect(Collectors.toList());
        orderExportRequest.setDetails(detailOrderExportRequest);
        if(!oderexport.getLogisticsCollection().isEmpty()){
            List<LogicticsRequest> logicticsRequests = oderexport.getLogisticsCollection().stream()
                    .map(this::converToLogisticsDTO).collect(Collectors.toList());
            orderExportRequest.setLogicticsRequests(logicticsRequests);
        }
        return orderExportRequest;
    }
    private DetailOrderExportRequest detailOrderExportRequest(Detailexportorder detailexportorder){
        DetailOrderExportRequest detailOrderExportRequest = new DetailOrderExportRequest();
        detailOrderExportRequest.setId(detailexportorder.getId());
        detailOrderExportRequest.setAmount(detailexportorder.getAmount());
        detailOrderExportRequest.setProductId(detailexportorder.getMaterial().getId());
        detailOrderExportRequest.setProductName(detailexportorder.getMaterial().getName());
        detailOrderExportRequest.setTotal_price(detailexportorder.getTotalPrice());
        return detailOrderExportRequest;
    }
    private LogicticsRequest converToLogisticsDTO(Logistics logistics){
        LogicticsRequest logicticsRequest = new LogicticsRequest();
        logicticsRequest.setId(logistics.getId());
        String type = logistics.getType().name();
        logicticsRequest.setType(type);
        logicticsRequest.setAddress(logistics.getWarehouse().getAddress());
        logicticsRequest.setWarehouseId(logistics.getWarehouse().getId());
        logicticsRequest.setWarehouseName(logistics.getWarehouse().getName());
        if(Logistics.Type.coming.equals(logistics.getType()))
            return logicticsRequest;
        if (Logistics.Type.arrived.equals(logistics.getType())){
            logicticsRequest.setEntryDate(logistics.getEntryDate());
            return logicticsRequest;
        }else {
            logicticsRequest.setEntryDate(logistics.getEntryDate());
            logicticsRequest.setExitDate(logistics.getExitDate());
            return logicticsRequest;
        }
    }
}

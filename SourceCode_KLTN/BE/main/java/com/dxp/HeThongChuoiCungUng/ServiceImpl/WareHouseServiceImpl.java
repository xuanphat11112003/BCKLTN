package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailOrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.WareHouseRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Repository.WareHouseRepository;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import com.dxp.HeThongChuoiCungUng.Specification.WareHouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Override
    public Warehouse findById(int id) {
        return this.wareHouseRepository.findById(id);
    }

    @Override
    public List<Warehouse> findAll(Map<String, String> params) {
        if(!params.isEmpty())
            return this.wareHouseRepository.findAll();
        else
        {
            Specification<Warehouse> specification = WareHouseSpecification.Search(params);
            return this.wareHouseRepository.findAll(specification);
        }
    }

    @Override
    public List<Warehouse> findAll() {
        return this.wareHouseRepository.findAll();
    }

    @Override
    public void save(Warehouse warehouse) {
        if (warehouse.getActive()) {
            List<Warehouse> activeWarehouses = this.wareHouseRepository.findByActiveTrue();
            if (!activeWarehouses.isEmpty()) {
                for (Warehouse activeWarehouse : activeWarehouses) {
                    activeWarehouse.setActive(false);
                    wareHouseRepository.save(activeWarehouse);
                }
            }
        }
        this.wareHouseRepository.save(warehouse);
    }

    @Override
    public Warehouse getWarehouseActive() {
        Warehouse warehouse = this.wareHouseRepository.findByActiveTrue().get(0);
        return warehouse;
    }
    @Override
    public WareHouseRequest findOrderInWareHouse(int id , Map<String,String> params) {
        Specification<Warehouse> specification = WareHouseSpecification.getOrderInWarehouse(id, params);
        List<Warehouse> warehouses = this.wareHouseRepository.findAll(specification);
        Warehouse warehouse = warehouses.isEmpty() ? null : warehouses.get(0);
        if(warehouse == null)
            return null;
        return this.convertToWareHouseDTO(warehouse);
    }

    @Override
    public void Delete(int id) {
        this.wareHouseRepository.delete(this.findById(id));
    }

    private WareHouseRequest convertToWareHouseDTO(Warehouse warehouse){
        List<OrderExportRequest> orderExportRequestList = new ArrayList<>();
        for(Logistics logistics : warehouse.getLogisticsCollection()){
            if (logistics.getType() != Logistics.Type.has_left)
                orderExportRequestList.add(this.convertToDTO(logistics.getLogistcs(),logistics.getType().name()));
            else
                continue;
        }
        WareHouseRequest wareHouseRequest = new WareHouseRequest();
        wareHouseRequest.setName(warehouse.getName());
        wareHouseRequest.setId(warehouse.getId());
        wareHouseRequest.setType(warehouse.getLogisticsCollection().stream().findFirst().map(Logistics::getType).orElse(null).toString());
        wareHouseRequest.setAddress(warehouse.getAddress());
        wareHouseRequest.setOrderExportRequestList(orderExportRequestList);
        return wareHouseRequest;

    }

    private OrderExportRequest convertToDTO(Oderexport oderexport,String type){
        OrderExportRequest orderExportRequest = new OrderExportRequest();
        orderExportRequest.setId(oderexport.getId());
        orderExportRequest.setOrderById(oderexport.getOrder_by().getId());
        orderExportRequest.setOrderByName(oderexport.getOrder_by().getUsername());
        orderExportRequest.setOrderForId(oderexport.getOrder_for().getId());
        orderExportRequest.setOrderForName(oderexport.getOrder_for().getUser().getUsername());
        orderExportRequest.setState(type);
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
}

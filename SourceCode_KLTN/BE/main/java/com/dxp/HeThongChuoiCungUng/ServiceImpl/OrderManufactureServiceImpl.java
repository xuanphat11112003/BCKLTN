package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderDetailManufactureRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderManufactureRequest;
import com.dxp.HeThongChuoiCungUng.Model.OrderDetailManufacture;
import com.dxp.HeThongChuoiCungUng.Model.OrderManufacture;
import com.dxp.HeThongChuoiCungUng.Repository.OrderManufactureRepository;
import com.dxp.HeThongChuoiCungUng.Service.OrderManufactureService;
import com.dxp.HeThongChuoiCungUng.Specification.OrdermanufactureSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderManufactureServiceImpl implements OrderManufactureService {
    @Autowired
    private OrderManufactureRepository orderManufactureRepository;

    @Override
    public void save(OrderManufacture orderManufacture) {
        this.orderManufactureRepository.save(orderManufacture);
    }

    @Override
    public OrderManufactureRequest findById(int id) {
        return convertToDTO(this.orderManufactureRepository.findById(id));
    }

    @Override
    public OrderManufacture getById(int id) {
        return this.orderManufactureRepository.findById(id);
    }

    @Override
    public void upDateTransaction(int id, String trans) {
        OrderManufacture.transactions tranc = OrderManufacture.transactions.fromValue(trans);
        OrderManufacture orderManufacture = this.getById(id);
        orderManufacture.setTransaction(tranc.getValue());
        this.save(orderManufacture);
    }

    @Override
    public void delete(int id) {
        this.orderManufactureRepository.delete(this.getById(id));
    }

    @Override
    public List<OrderManufactureRequest> findAll(Map<String, String> params) {
        Specification<OrderManufacture> orderManufactureSpecification = OrdermanufactureSpecification.getOrderManufacture(params);
        List<OrderManufacture> orderManufactures = this.orderManufactureRepository.findAll(orderManufactureSpecification);
        return orderManufactures.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private OrderManufactureRequest convertToDTO(OrderManufacture orderManufacture){
        OrderManufactureRequest orderManufactureRequest = new OrderManufactureRequest();
        orderManufactureRequest.setId(orderManufacture.getId());
        orderManufactureRequest.setOrderById(orderManufacture.getOrder_by().getId());
        orderManufactureRequest.setOrderByName(orderManufacture.getOrder_by().getUsername());
        orderManufactureRequest.setActive(orderManufacture.getActive());
        orderManufactureRequest.setCreate_date(orderManufacture.getCreateDate());
        orderManufactureRequest.setTransaction(orderManufacture.getTransaction());
        orderManufactureRequest.setWareHouseId(orderManufacture.getWarehouseID().getId());
        orderManufactureRequest.setWareHouseName(orderManufacture.getWarehouseID().getName());
        List<OrderDetailManufactureRequest> detailDTOs = orderManufacture.getOrderDetailManufactureCollection().stream()
                .map(this::convertDetailToDTO).collect(Collectors.toList());
        orderManufactureRequest.setDetails(detailDTOs);
        return orderManufactureRequest;
    }
    private OrderDetailManufactureRequest convertDetailToDTO(OrderDetailManufacture orderDetailManufacture){
        OrderDetailManufactureRequest orderDetailManufactureRequest = new OrderDetailManufactureRequest();
        orderDetailManufactureRequest.setId(orderDetailManufacture.getId());
        orderDetailManufactureRequest.setQuantity(orderDetailManufacture.getQuantity());
        orderDetailManufactureRequest.setProductId(orderDetailManufacture.getProduct().getId());
        orderDetailManufactureRequest.setProductName(orderDetailManufacture.getProduct().getName());
        return orderDetailManufactureRequest;
    }

}

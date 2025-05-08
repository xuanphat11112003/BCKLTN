package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.OrderDetailManufacture;
import com.dxp.HeThongChuoiCungUng.Repository.OrderDetailManufactureRepository;
import com.dxp.HeThongChuoiCungUng.Service.OrderDetailManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailManufactureServiceImpl implements OrderDetailManufactureService {
    @Autowired
    private OrderDetailManufactureRepository orderDetailManufactureRepository;
    @Override
    public void save(OrderDetailManufacture orderDetailManufacture) {
        this.orderDetailManufactureRepository.save(orderDetailManufacture);
    }

    @Override
    public List<ProductRequest> getProductProductionData() {
        return orderDetailManufactureRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        detail -> detail.getProduct().getName(),
                        Collectors.summingInt(OrderDetailManufacture::getQuantity)
                ))
                .entrySet().stream()
                .map(entry -> new ProductRequest(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

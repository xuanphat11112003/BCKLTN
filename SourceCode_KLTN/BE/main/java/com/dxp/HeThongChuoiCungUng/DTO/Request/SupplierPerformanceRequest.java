package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SupplierPerformanceRequest {
    private int id;
    private int supplierId;
    private String supplierName;
    private Date evaluateDate;
    private int deliveryRating;
    private int qualityRating;
    private int priceRating;
    private String comment;
    private int userId;
    private String userName;
    private int orderId;
}

package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupplierResponse {
    private int id;
    private String name;
    private Double deliveryRatings;
    private Double qualityRatings;
    private Double priceRatings;
}

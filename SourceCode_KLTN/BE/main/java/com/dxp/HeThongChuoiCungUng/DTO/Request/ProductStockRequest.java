package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockRequest {
    private int id;
    private int amount;
    private int productId;
    private String productName;
    private boolean active;

}

package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MaterialStockRequest {
    private int id;
    private int amount;
    private int materialId;
    private String materialName;
    private Date date_expire;
    private boolean active;
    private String supplierName;
    private int supplierId;
    private int remain;
}

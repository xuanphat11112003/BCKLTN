package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Manufacturerequest {
    private int id;
    private int amount;
    private String unit;
    private int productId;
    private String productName;
    private int materialId;
    private String materialName;
    private int weight;
    private String agencyMaterial;
    private long price;
}

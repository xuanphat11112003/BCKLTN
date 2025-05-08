package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderExportRequest {
    private int id;
    private int amount;
    private long total_price;
    private int productId;
    private String productName;
}

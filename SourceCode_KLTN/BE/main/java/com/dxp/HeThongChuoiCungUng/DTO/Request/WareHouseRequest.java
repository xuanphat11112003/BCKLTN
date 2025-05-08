package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WareHouseRequest {
    private int id;
    private String name;
    private String address;
    private String type;
    private List<OrderExportRequest> orderExportRequestList;
}

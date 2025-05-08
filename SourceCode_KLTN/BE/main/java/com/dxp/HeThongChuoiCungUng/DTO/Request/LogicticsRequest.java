package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class LogicticsRequest {
    private int id;
    private String type;
    private Date entryDate;
    private Date exitDate;
    private String warehouseName;
    private String address;
    private int warehouseId;
}

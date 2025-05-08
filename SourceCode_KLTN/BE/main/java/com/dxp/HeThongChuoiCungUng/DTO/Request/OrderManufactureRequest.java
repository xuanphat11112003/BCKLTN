package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderManufactureRequest {
    private int id;
    private Date create_date;
    private String transaction;
    private boolean active;
    private int orderById;
    private String orderByName;
    private int wareHouseId;
    private String wareHouseName;
    private List<OrderDetailManufactureRequest> details;
}

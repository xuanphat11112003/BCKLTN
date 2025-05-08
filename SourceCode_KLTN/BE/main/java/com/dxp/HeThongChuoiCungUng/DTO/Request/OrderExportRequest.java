package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderExportRequest {
    private int id;
    private String state;
    private String typeBuy;
    private long totalPrice;
    private Date createDate;
    private int orderById;
    private String orderByName;
    private int orderForId;
    private String orderForName;
    private int transPrice;
    private int transId;
    private String transName;
    private int wareHousePrice;
    private List<DetailOrderExportRequest> details;
    private List<LogicticsRequest> logicticsRequests;
}

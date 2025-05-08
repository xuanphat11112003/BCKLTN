package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PartnerRequest {
    private int id;
    private boolean active;
    private String contractInfo;
    private Date createDate;
    private Date entryDate;
    private String name;
    private String type;
    private int agencyId;
    private String agencyName;
    private int transPortId;
    private String transPortName;
}

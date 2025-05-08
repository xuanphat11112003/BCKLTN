package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AgencyRequest {
    private Integer id;
    private int mangerName;
    private Integer userId;
    private String userName;
    private String address;
    private String avatar;
    private Date createDate;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String user_Role;
    private PartnerRequest partnerRequest;
}

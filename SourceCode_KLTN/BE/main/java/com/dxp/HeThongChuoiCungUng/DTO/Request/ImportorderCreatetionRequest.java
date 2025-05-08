package com.dxp.HeThongChuoiCungUng.DTO.Request;



import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Getter
public class ImportorderCreatetionRequest {
    private int id;
    private Date orderDate;
    private Long totalCost;
    private String payment;
    private boolean active;
    private boolean activeEvaluate;
    private String importedBy;
    private List<DetailImportOrderCreatetionRequest> detail;

    public ImportorderCreatetionRequest() {
    }

}

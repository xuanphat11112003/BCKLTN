package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MaterialInventoryRequest {
        private int id;
        private Date entryDate;
        private Date exitDate;
        private int wareHouseId;
        private String wareHouseName;
        List<MaterialStockRequest> materialStock;
        List<ProductStockRequest> productStockRequests;
}

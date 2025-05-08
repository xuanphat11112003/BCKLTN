package com.dxp.HeThongChuoiCungUng.DTO.Request;

import com.dxp.HeThongChuoiCungUng.Model.Material;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequest {
    private int id;
    private String name;
    private String unit;
    private int weight;
    private Long price;
    private int categoryId;
    private int SupplierId;
    private String SupplierName;
    public MaterialRequest(Material ma){
        this.id = ma.getId();
        this.name = ma.getName();
        this.unit = ma.getUnit();
        this.weight = ma.getWeight();
        this.SupplierId = ma.getSupplierId().getId();
        this.SupplierName = ma.getSupplierId().getName();
        this.price = ma.getPrice();
        this.categoryId = ma.getCategoryId().getId();
    }
}

package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private int id;
    private String name;
    private Long price;
    private String image;
    private int quantity;
    public ProductRequest(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
}

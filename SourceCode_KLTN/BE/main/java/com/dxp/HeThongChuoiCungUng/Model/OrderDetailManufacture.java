package com.dxp.HeThongChuoiCungUng.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Entity
@Table(name = "orderDetailManufacture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailManufacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private Integer quantity;
    @JoinColumn(name = "OrderManufacture_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderManufacture orderManufacture;
    @JoinColumn(name = "Product_id",referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
}

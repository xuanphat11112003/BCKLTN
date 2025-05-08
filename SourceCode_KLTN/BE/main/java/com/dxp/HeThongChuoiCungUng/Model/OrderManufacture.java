package com.dxp.HeThongChuoiCungUng.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "OrderManufacture")
@Setter
@AllArgsConstructor
@Data
@Getter
@NoArgsConstructor
public class OrderManufacture implements Serializable {
    public enum transactions{
        V1("Đang đợi hàng về"),
        V2("Đã Nhận"),
        V3("Đang thực hiện"),
        V4("Đã hoàn tất");
        private String value;
        transactions(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static transactions fromValue(String value){
            for (transactions trans : transactions.values()){
                if(trans.getValue().equals(value))
                    return trans;
            }
            throw new IllegalArgumentException("Unknown transactions type: " + value);
        }

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CreateDate")
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "Transaction")
    private String transaction;
    @Basic(optional = false)
    @Column(name = "active" ,columnDefinition = "boolean default true")
    private Boolean active;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "orderManufacture")
    private Collection<OrderDetailManufacture> orderDetailManufactureCollection;
    @JoinColumn(name = "order_by", referencedColumnName = "id")
    @ManyToOne
    private User order_by;
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Warehouse warehouseID;
}

package com.dxp.HeThongChuoiCungUng.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Logistics")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Logistics implements Serializable {
    public static enum Type{
        coming, arrived, has_left;
        public static Type fromvalue (String value){
            for(Type type : Type.values()){
                if(type.name().equalsIgnoreCase(value)){
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid value for CategoryType: " + value);
        }
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "entryDate")
    private Date entryDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Column(name = "exitDate")
    private Date exitDate;
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Warehouse warehouse;
    @JoinColumn(name = "order_exportId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Oderexport Logistcs;
}

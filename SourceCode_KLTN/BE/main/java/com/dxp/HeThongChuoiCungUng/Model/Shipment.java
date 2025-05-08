/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "shipment")
@NamedQueries({
    @NamedQuery(name = "Shipment.findAll", query = "SELECT s FROM Shipment s"),
    @NamedQuery(name = "Shipment.findById", query = "SELECT s FROM Shipment s WHERE s.id = :id"),
    @NamedQuery(name = "Shipment.findByPrice", query = "SELECT s FROM Shipment s WHERE s.price = :price")})
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name="express_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transportpartner expressId;
    @OneToOne
    @JoinColumn(name = "oderid", referencedColumnName = "id")
    private Oderexport oderid;
    @Column(name = "price")
    private Long price;



    public Shipment() {
    }

    public Shipment(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transportpartner getExpressId() {
        return expressId;
    }

    public void setExpressId(Transportpartner expressId) {
        this.expressId = expressId;
    }

    public Oderexport getOderid() {
        return oderid;
    }

    public void setOderid(Oderexport oderid) {
        this.oderid = oderid;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shipment)) {
            return false;
        }
        Shipment other = (Shipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Shipment[ id=" + id + " ]";
    }
    
}

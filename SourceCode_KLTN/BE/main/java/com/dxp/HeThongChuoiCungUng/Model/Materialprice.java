/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "materialprice")
@NamedQueries({
    @NamedQuery(name = "Materialprice.findAll", query = "SELECT m FROM Materialprice m"),
    @NamedQuery(name = "Materialprice.findById", query = "SELECT m FROM Materialprice m WHERE m.id = :id"),
    @NamedQuery(name = "Materialprice.findByPrice", query = "SELECT m FROM Materialprice m WHERE m.price = :price"),
    @NamedQuery(name = "Materialprice.findByPriceDate", query = "SELECT m FROM Materialprice m WHERE m.priceDate = :priceDate")})
public class Materialprice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @Column(name = "price_date")
    @Temporal(TemporalType.DATE)
    private Date priceDate;
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material materialId;

    public Materialprice() {
    }

    public Materialprice(Integer id) {
        this.id = id;
    }

    public Materialprice(Integer id, BigDecimal price, Date priceDate) {
        this.id = id;
        this.price = price;
        this.priceDate = priceDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public Material getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Material materialId) {
        this.materialId = materialId;
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
        if (!(object instanceof Materialprice)) {
            return false;
        }
        Materialprice other = (Materialprice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Materialprice[ id=" + id + " ]";
    }
    
}

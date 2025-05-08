/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "detailexportorder")
@NamedQueries({
    @NamedQuery(name = "Detailexportorder.findAll", query = "SELECT d FROM Detailexportorder d"),
    @NamedQuery(name = "Detailexportorder.findById", query = "SELECT d FROM Detailexportorder d WHERE d.id = :id"),
    @NamedQuery(name = "Detailexportorder.findByAmount", query = "SELECT d FROM Detailexportorder d WHERE d.amount = :amount"),
    @NamedQuery(name = "Detailexportorder.findByDiscount", query = "SELECT d FROM Detailexportorder d WHERE d.discount = :discount"),
    @NamedQuery(name = "Detailexportorder.findByTotalPrice", query = "SELECT d FROM Detailexportorder d WHERE d.totalPrice = :totalPrice")})
public class Detailexportorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @Column(name = "discount")
    private Long discount;
    @Basic(optional = false)
    @Column(name = "total_price")
    private Long totalPrice;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "export_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oderexport exportOrderId;

    public Detailexportorder() {
    }

    public Detailexportorder(Integer id) {
        this.id = id;
    }

    public Detailexportorder(Integer id, int amount, Long discount, Long totalPrice) {
        this.id = id;
        this.amount = amount;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getMaterial() {
        return productId;
    }

    public void setMaterial(Product material) {
        this.productId = material;
    }

    public Oderexport getExportOrderId() {
        return exportOrderId;
    }

    public void setExportOrderId(Oderexport exportOrderId) {
        this.exportOrderId = exportOrderId;
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
        if (!(object instanceof Detailexportorder)) {
            return false;
        }
        Detailexportorder other = (Detailexportorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Detailexportorder[ id=" + id + " ]";
    }
    
}

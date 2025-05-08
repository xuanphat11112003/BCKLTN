/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
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

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "detailimportorder")
@NamedQueries({
    @NamedQuery(name = "Detailimportorder.findAll", query = "SELECT d FROM Detailimportorder d"),
    @NamedQuery(name = "Detailimportorder.findById", query = "SELECT d FROM Detailimportorder d WHERE d.id = :id"),
    @NamedQuery(name = "Detailimportorder.findByQuantity", query = "SELECT d FROM Detailimportorder d WHERE d.quantity = :quantity"),
    @NamedQuery(name = "Detailimportorder.findByTotalAmount", query = "SELECT d FROM Detailimportorder d WHERE d.totalAmount = :totalAmount")})
public class Detailimportorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "total_amount")
    private Long totalAmount;
    @JoinColumn(name = "ImportOrder_ID", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Importorder importOrderID;
    @JoinColumn(name = "Material_ID", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material materialID;

    public Detailimportorder() {
    }

    public Detailimportorder(Integer id) {
        this.id = id;
    }

    public Detailimportorder(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Importorder getImportOrderID() {
        return importOrderID;
    }

    public void setImportOrderID(Importorder importOrderID) {
        this.importOrderID = importOrderID;
    }

    public Material getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Material materialID) {
        this.materialID = materialID;
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
        if (!(object instanceof Detailimportorder)) {
            return false;
        }
        Detailimportorder other = (Detailimportorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Detailimportorder[ id=" + id + " ]";
    }
    
}

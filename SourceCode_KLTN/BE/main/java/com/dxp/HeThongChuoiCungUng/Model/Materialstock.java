/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "materialstock")
@NamedQueries({
    @NamedQuery(name = "Materialstock.findAll", query = "SELECT m FROM Materialstock m"),
    @NamedQuery(name = "Materialstock.findById", query = "SELECT m FROM Materialstock m WHERE m.id = :id"),
    @NamedQuery(name = "Materialstock.findByAmount", query = "SELECT m FROM Materialstock m WHERE m.amount = :amount"),
    @NamedQuery(name = "Materialstock.findByDateExpiry", query = "SELECT m FROM Materialstock m WHERE m.dateExpiry = :dateExpiry")})
public class Materialstock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;
    @Column(name = "date_expiry")
    @Temporal(TemporalType.DATE)
    private Date dateExpiry;
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    private Inventory inventoryId;
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material materialId;
    @Column(name = "active",columnDefinition = "boolean default true")
    private Boolean active;
    @Column(name = "remain",columnDefinition = "int default 0")
    private int remain;

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }



    public Materialstock() {
    }

    public Materialstock(Integer id) {
        this.id = id;
    }

    public Materialstock(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
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

    public Date getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(Date dateExpiry) {
        this.dateExpiry = dateExpiry;
    }



    public Inventory getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Inventory inventoryId) {
        this.inventoryId = inventoryId;
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
        if (!(object instanceof Materialstock)) {
            return false;
        }
        Materialstock other = (Materialstock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Materialstock[ id=" + id + " ]";
    }
    
}

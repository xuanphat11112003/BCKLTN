/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "warehouse")
@NamedQueries({
    @NamedQuery(name = "Warehouse.findAll", query = "SELECT w FROM Warehouse w"),
    @NamedQuery(name = "Warehouse.findById", query = "SELECT w FROM Warehouse w WHERE w.id = :id"),
    @NamedQuery(name = "Warehouse.findByName", query = "SELECT w FROM Warehouse w WHERE w.name = :name"),
    @NamedQuery(name = "Warehouse.findByAddress", query = "SELECT w FROM Warehouse w WHERE w.address = :address")})
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    @NotEmpty(message = "Tên kho không được để trống")
    private String name;
    @Basic(optional = false)
    @NotEmpty(message = "Địa chỉ không được để trống")
    @Column(name = "address")
    private String address;
    @Column(name ="active", nullable = false, columnDefinition = "boolean default false")
    private Boolean active;
    @OneToMany(mappedBy = "position")
    private Collection<Employee> employeeCollection;
    @OneToMany(mappedBy = "warehouse")
    private Collection<Inventory> inventoryCollection;
    @OneToMany(mappedBy = "warehouseID", cascade = CascadeType.ALL)
    private Collection<OrderManufacture> orderDetailManufactureCollection;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private Collection<Logistics> logisticsCollection;

    public Collection<OrderManufacture> getOrderDetailManufactureCollection() {
        return orderDetailManufactureCollection;
    }

    public void setOrderDetailManufactureCollection(Collection<OrderManufacture> orderDetailManufactureCollection) {
        this.orderDetailManufactureCollection = orderDetailManufactureCollection;
    }

    public Warehouse() {
    }

    public Warehouse(Integer id) {
        this.id = id;
    }

    public Warehouse(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Collection<Logistics> getLogisticsCollection() {
        return logisticsCollection;
    }

    public void setLogisticsCollection(Collection<Logistics> logisticsCollection) {
        this.logisticsCollection = logisticsCollection;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    public Collection<Inventory> getInventoryCollection() {
        return inventoryCollection;
    }

    public void setInventoryCollection(Collection<Inventory> inventoryCollection) {
        this.inventoryCollection = inventoryCollection;
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
        if (!(object instanceof Warehouse)) {
            return false;
        }
        Warehouse other = (Warehouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Warehouse[ id=" + id + " ]";
    }
    
}

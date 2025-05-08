/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.*;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "transportpartner")
@NamedQueries({
    @NamedQuery(name = "Transportpartner.findAll", query = "SELECT t FROM Transportpartner t"),
    @NamedQuery(name = "Transportpartner.findById", query = "SELECT t FROM Transportpartner t WHERE t.id = :id"),
    @NamedQuery(name = "Transportpartner.findByName", query = "SELECT t FROM Transportpartner t WHERE t.name = :name"),
    @NamedQuery(name = "Transportpartner.findByEmail", query = "SELECT t FROM Transportpartner t WHERE t.email = :email"),
    @NamedQuery(name = "Transportpartner.findByNumberPhone", query = "SELECT t FROM Transportpartner t WHERE t.numberPhone = :numberPhone"),
    @NamedQuery(name = "Transportpartner.findByAddress", query = "SELECT t FROM Transportpartner t WHERE t.address = :address"),})
public class Transportpartner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "NumberPhone")
    private String numberPhone;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "transportpartner")
    private Collection<partnerTransport> transportCollection;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "expressId")
    private Collection<Shipment> shipmentCollection;

    public Transportpartner() {
    }

    public Transportpartner(Integer id) {
        this.id = id;
    }

    public Transportpartner(Integer id, String name, String email, String numberPhone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public Collection<Shipment> getShipmentCollection() {
        return shipmentCollection;
    }

    public void setShipmentCollection(Collection<Shipment> shipmentCollection) {
        this.shipmentCollection = shipmentCollection;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Collection<partnerTransport> getTransportCollection() {
        return transportCollection;
    }

    public void setTransportCollection(Collection<partnerTransport> transportCollection) {
        this.transportCollection = transportCollection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (!(object instanceof Transportpartner)) {
            return false;
        }
        Transportpartner other = (Transportpartner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Transportpartner[ id=" + id + " ]";
    }
    
}

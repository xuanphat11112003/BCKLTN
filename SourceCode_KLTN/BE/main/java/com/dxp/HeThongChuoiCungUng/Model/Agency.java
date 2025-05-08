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
@Table(name = "agency")
@NamedQueries({
    @NamedQuery(name = "Agency.findAll", query = "SELECT a FROM Agency a"),
    @NamedQuery(name = "Agency.findById", query = "SELECT a FROM Agency a WHERE a.id = :id"),
    @NamedQuery(name = "Agency.findByManagerName", query = "SELECT a FROM Agency a WHERE a.managerName = :managerName")})
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ManagerName")
    private String managerName;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private User userAID;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order_for")
    private Collection<Oderexport> oderexports;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "agency")
    private Collection<partnerAgency> partnerAgencyCollection;

    public Agency() {
    }

    public Agency(Integer userid) {
        this.id = userid;
    }

    public Agency(Integer userid, String managerName) {
        this.id = userid;
        this.managerName = managerName;
    }

    public Collection<partnerAgency> getPartnerAgencyCollection() {
        return partnerAgencyCollection;
    }

    public void setPartnerAgencyCollection(Collection<partnerAgency> partnerAgencyCollection) {
        this.partnerAgencyCollection = partnerAgencyCollection;
    }

    public Collection<Oderexport> getOderexports() {
        return oderexports;
    }

    public void setOderexports(Collection<Oderexport> oderexports) {
        this.oderexports = oderexports;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userid) {
        this.id = userid;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }




    public User getUser() {
        return userAID;
    }

    public void setUser(User user) {
        this.userAID = user;
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
        if (!(object instanceof Agency)) {
            return false;
        }
        Agency other = (Agency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Agency[ userid=" + id + " ]";
    }
    
}

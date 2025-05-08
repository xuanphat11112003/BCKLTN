/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "partner")
@NamedQueries({
    @NamedQuery(name = "Partner.findAll", query = "SELECT p FROM Partner p"),
    @NamedQuery(name = "Partner.findById", query = "SELECT p FROM Partner p WHERE p.id = :id"),
    @NamedQuery(name = "Partner.findByName", query = "SELECT p FROM Partner p WHERE p.name = :name"),
    @NamedQuery(name = "Partner.findByContactInfo", query = "SELECT p FROM Partner p WHERE p.contactInfo = :contactInfo"),
    @NamedQuery(name = "Partner.findByType", query = "SELECT p FROM Partner p WHERE p.type = :type"),
    @NamedQuery(name = "Partner.findByCreateDate", query = "SELECT p FROM Partner p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "Partner.findByEndDate", query = "SELECT p FROM Partner p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Partner.findByActive", query = "SELECT p FROM Partner p WHERE p.active = :active")})
public class Partner implements Serializable {
    public enum Type{
        AGENCY, TRANSPORT;
        public static Type fromValue(String value){
            for(Type type1 : Type.values())
                if(type1.name().equalsIgnoreCase(value))
                    return type1;
            throw new IllegalArgumentException("Invalid value for CategoryType: " + value);
        }

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "contact_info")
    private String contactInfo;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Basic(optional = false)
    @Column(name = "Create_Date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "End_Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner_Id")
    private Collection<Performanevaluation> performanevaluationCollection;
    @OneToOne(mappedBy = "partner", cascade = CascadeType.ALL)
    private partnerAgency partnerAgency;
    @OneToOne(mappedBy = "partner_trans",cascade = CascadeType.ALL)
    private partnerTransport partnerTransport;


    public Partner() {
    }

    public Partner(Integer id) {
        this.id = id;
    }

    public Partner(Integer id, String name, String contactInfo, Type type, Date createDate, Date endDate, Boolean active) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.type = type;
        this.createDate = createDate;
        this.endDate = endDate;
        this.active = active;
    }

    public com.dxp.HeThongChuoiCungUng.Model.partnerTransport getPartnerTransport() {
        return partnerTransport;
    }

    public void setPartnerTransport(com.dxp.HeThongChuoiCungUng.Model.partnerTransport partnerTransport) {
        this.partnerTransport = partnerTransport;
    }

    public com.dxp.HeThongChuoiCungUng.Model.partnerAgency getPartnerAgency() {
        return partnerAgency;
    }

    public void setPartnerAgency(com.dxp.HeThongChuoiCungUng.Model.partnerAgency partnerAgency) {
        this.partnerAgency = partnerAgency;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Collection<Performanevaluation> getPerformanevaluationCollection() {
        return performanevaluationCollection;
    }

    public void setPerformanevaluationCollection(Collection<Performanevaluation> performanevaluationCollection) {
        this.performanevaluationCollection = performanevaluationCollection;
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
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Partner[ id=" + id + " ]";
    }
    
}

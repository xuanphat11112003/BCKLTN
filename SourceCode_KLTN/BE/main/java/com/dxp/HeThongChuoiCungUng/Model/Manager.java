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
@Table(name = "manager")
@NamedQueries({
    @NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m"),
    @NamedQuery(name = "Manager.findByUserId", query = "SELECT m FROM Manager m WHERE m.Id = :userId"),
    @NamedQuery(name = "Manager.findByCreatedate", query = "SELECT m FROM Manager m WHERE m.createdate = :createdate"),
    @NamedQuery(name = "Manager.findByEndDate", query = "SELECT m FROM Manager m WHERE m.endDate = :endDate")})
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer userId;
    @Basic
    @Column(name = "Create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date createdate;
    @Basic
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @OneToMany(mappedBy = "managerId")
    private Collection<Supplierperformance> supplierperformanceCollection;
    @JoinColumn(name = "UserId", referencedColumnName = "id")
    @OneToOne
    private User user;

    public Manager() {
    }

    public Manager(Integer userId) {
        this.userId = userId;
    }

    public Manager(Integer userId, Date createdate, Date endDate) {
        this.userId = userId;
        this.createdate = createdate;
        this.endDate = endDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Collection<Supplierperformance> getSupplierperformanceCollection() {
        return supplierperformanceCollection;
    }

    public void setSupplierperformanceCollection(Collection<Supplierperformance> supplierperformanceCollection) {
        this.supplierperformanceCollection = supplierperformanceCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Manager[ userId=" + userId + " ]";
    }
    
}

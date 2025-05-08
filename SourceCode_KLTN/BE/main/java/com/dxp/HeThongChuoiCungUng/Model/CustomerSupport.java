/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
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
@Table(name = "customer_support")
@NamedQueries({
    @NamedQuery(name = "CustomerSupport.findAll", query = "SELECT c FROM CustomerSupport c"),
    @NamedQuery(name = "CustomerSupport.findById", query = "SELECT c FROM CustomerSupport c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerSupport.findByCustomerName", query = "SELECT c FROM CustomerSupport c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "CustomerSupport.findByPhoneName", query = "SELECT c FROM CustomerSupport c WHERE c.phoneName = :phoneName"),
    @NamedQuery(name = "CustomerSupport.findByIssueDescription", query = "SELECT c FROM CustomerSupport c WHERE c.issueDescription = :issueDescription"),
    @NamedQuery(name = "CustomerSupport.findByStatus", query = "SELECT c FROM CustomerSupport c WHERE c.status = :status"),
    @NamedQuery(name = "CustomerSupport.findByCreateDate", query = "SELECT c FROM CustomerSupport c WHERE c.createDate = :createDate")})
public class CustomerSupport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CustomerName")
    private String customerName;
    @Basic(optional = false)
    @Column(name = "PhoneName")
    private String phoneName;
    @Basic(optional = false)
    @Column(name = "issue_description")
    private String issueDescription;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public CustomerSupport() {
    }

    public CustomerSupport(Integer id) {
        this.id = id;
    }

    public CustomerSupport(Integer id, String customerName, String phoneName, String issueDescription, String status, Date createDate) {
        this.id = id;
        this.customerName = customerName;
        this.phoneName = phoneName;
        this.issueDescription = issueDescription;
        this.status = status;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof CustomerSupport)) {
            return false;
        }
        CustomerSupport other = (CustomerSupport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.CustomerSupport[ id=" + id + " ]";
    }
    
}

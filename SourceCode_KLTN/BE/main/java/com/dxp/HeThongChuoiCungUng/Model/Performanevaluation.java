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
@Table(name = "performanevaluation")
@NamedQueries({
    @NamedQuery(name = "Performanevaluation.findAll", query = "SELECT p FROM Performanevaluation p"),
    @NamedQuery(name = "Performanevaluation.findById", query = "SELECT p FROM Performanevaluation p WHERE p.id = :id"),
    @NamedQuery(name = "Performanevaluation.findByCreateDate", query = "SELECT p FROM Performanevaluation p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "Performanevaluation.findByRating", query = "SELECT p FROM Performanevaluation p WHERE p.rating = :rating"),
    @NamedQuery(name = "Performanevaluation.findByComments", query = "SELECT p FROM Performanevaluation p WHERE p.comments = :comments")})
public class Performanevaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "Rating")
    private int rating;
    @Basic(optional = false)
    @Column(name = "comments")
    private String comments;
    @JoinColumn(name = "PartnerId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Partner partner_Id;
    @JoinColumn(name = "UserId", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Performanevaluation() {
    }

    public Performanevaluation(Integer id) {
        this.id = id;
    }

    public Performanevaluation(Integer id, Date createDate, int rating, String comments) {
        this.id = id;
        this.createDate = createDate;
        this.rating = rating;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Partner getPartnerId() {
        return partner_Id;
    }

    public void setPartnerId(Partner partnerId) {
        this.partner_Id = partnerId;
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
        if (!(object instanceof Performanevaluation)) {
            return false;
        }
        Performanevaluation other = (Performanevaluation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Performanevaluation[ id=" + id + " ]";
    }
    
}

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import jakarta.persistence.JoinColumn;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "detailsexportordercost")
@NamedQueries({
    @NamedQuery(name = "Detailsexportordercost.findAll", query = "SELECT d FROM Detailsexportordercost d"),
    @NamedQuery(name = "Detailsexportordercost.findById", query = "SELECT d FROM Detailsexportordercost d WHERE d.id = :id"),
    @NamedQuery(name = "Detailsexportordercost.findByCreateDate", query = "SELECT d FROM Detailsexportordercost d WHERE d.createDate = :createDate"),
    @NamedQuery(name = "Detailsexportordercost.findByDescription", query = "SELECT d FROM Detailsexportordercost d WHERE d.description = :description")})
public class Detailsexportordercost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "description")
    private Integer description;
    @JoinColumn(name = "cost", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cost cost;
    @JoinColumn(name = "exportorder_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oderexport exportorderId;

    public Detailsexportordercost() {
    }

    public Detailsexportordercost(Integer id) {
        this.id = id;
    }

    public Detailsexportordercost(Integer id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Oderexport getExportorderId() {
        return exportorderId;
    }

    public void setExportorderId(Oderexport exportorderId) {
        this.exportorderId = exportorderId;
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
        if (!(object instanceof Detailsexportordercost)) {
            return false;
        }
        Detailsexportordercost other = (Detailsexportordercost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Detailsexportordercost[ id=" + id + " ]";
    }
    
}

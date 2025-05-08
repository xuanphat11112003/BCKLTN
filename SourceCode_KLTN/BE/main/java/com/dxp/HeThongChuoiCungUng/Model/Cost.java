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
@Table(name = "cost")
@NamedQueries({
    @NamedQuery(name = "Cost.findAll", query = "SELECT c FROM Cost c"),
    @NamedQuery(name = "Cost.findById", query = "SELECT c FROM Cost c WHERE c.id = :id"),
    @NamedQuery(name = "Cost.findByType", query = "SELECT c FROM Cost c WHERE c.type = :type"),
    @NamedQuery(name = "Cost.findByPrice", query = "SELECT c FROM Cost c WHERE c.price = :price")})
public class Cost implements Serializable {
    public static  enum Type{
        Transport, Warehouse;
        public static Type fromValue(String value){
            for(Type type1 : Type.values()){
                if(type1.name().equalsIgnoreCase(value))
                    return type1;
            }
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
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Basic(optional = false)
    @Column(name = "price")
    private Integer price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cost")
    private Collection<Detailsexportordercost> detailsexportordercostCollection;

    public Cost() {
    }

    public Cost(Integer id) {
        this.id = id;
    }

    public Cost(Integer id, Type type, int price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Collection<Detailsexportordercost> getDetailsexportordercostCollection() {
        return detailsexportordercostCollection;
    }

    public void setDetailsexportordercostCollection(Collection<Detailsexportordercost> detailsexportordercostCollection) {
        this.detailsexportordercostCollection = detailsexportordercostCollection;
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
        if (!(object instanceof Cost)) {
            return false;
        }
        Cost other = (Cost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Cost[ id=" + id + " ]";
    }
    
}

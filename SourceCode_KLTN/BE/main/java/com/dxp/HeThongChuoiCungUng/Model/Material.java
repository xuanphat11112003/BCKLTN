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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "material")
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findById", query = "SELECT m FROM Material m WHERE m.id = :id"),
    @NamedQuery(name = "Material.findByName", query = "SELECT m FROM Material m WHERE m.name = :name"),
    @NamedQuery(name = "Material.findByPrice", query = "SELECT m FROM Material m WHERE m.price = :price"),
    @NamedQuery(name = "Material.findByUnit", query = "SELECT m FROM Material m WHERE m.unit = :unit"),
    @NamedQuery(name = "Material.findByWeight", query = "SELECT m FROM Material m WHERE m.weight = :weight")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    private Long price;
    @Basic(optional = false)
    @Column(name = "unit")
    private String unit;
    @Column(name = "weight")
    private Integer weight;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private Collection<Materialstock> materialstockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialID")
    private Collection<Detailimportorder> detailimportorderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private Collection<Manufacture> manufactureCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private Collection<Materialprice> materialpriceCollection;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @ManyToOne
    private Supplier supplierId;

    public Material() {
    }

    public Material(Integer id) {
        this.id = id;
    }

    public Material(Integer id, String name, Long price, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Collection<Materialstock> getMaterialstockCollection() {
        return materialstockCollection;
    }

    public void setMaterialstockCollection(Collection<Materialstock> materialstockCollection) {
        this.materialstockCollection = materialstockCollection;
    }

    public Collection<Detailimportorder> getDetailimportorderCollection() {
        return detailimportorderCollection;
    }

    public void setDetailimportorderCollection(Collection<Detailimportorder> detailimportorderCollection) {
        this.detailimportorderCollection = detailimportorderCollection;
    }

    public Collection<Manufacture> getManufactureCollection() {
        return manufactureCollection;
    }

    public void setManufactureCollection(Collection<Manufacture> manufactureCollection) {
        this.manufactureCollection = manufactureCollection;
    }

    public Collection<Materialprice> getMaterialpriceCollection() {
        return materialpriceCollection;
    }

    public void setMaterialpriceCollection(Collection<Materialprice> materialpriceCollection) {
        this.materialpriceCollection = materialpriceCollection;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
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
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Material[ id=" + id + " ]";
    }
    
}

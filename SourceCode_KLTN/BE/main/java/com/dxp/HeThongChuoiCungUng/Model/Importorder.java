/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "importorder")
@NamedQueries({
    @NamedQuery(name = "Importorder.findAll", query = "SELECT i FROM Importorder i"),
    @NamedQuery(name = "Importorder.findById", query = "SELECT i FROM Importorder i WHERE i.id = :id"),
    @NamedQuery(name = "Importorder.findByOrderDate", query = "SELECT i FROM Importorder i WHERE i.orderDate = :orderDate"),
    @NamedQuery(name = "Importorder.findByTotalCost", query = "SELECT i FROM Importorder i WHERE i.totalCost = :totalCost"),
    @NamedQuery(name = "Importorder.findByPayment", query = "SELECT i FROM Importorder i WHERE i.payment = :payment")})
public class Importorder implements Serializable {
    public enum Payment {
        V1("Thanh Toán Ngay Lập Tức"),
        V2("Thanh Toán Sau 1 Ngày Nhận Hóa Đơn"),
        V3("Thanh toán Sau khi nhận hàng");

        private String value;

        Payment(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Payment fromValue(String value) {
            for (Payment payment : Payment.values()) {
                if (payment.getValue().equals(value)) {
                    return payment;
                }
            }
            throw new IllegalArgumentException("Unknown payment type: " + value);
        }
    }


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Column(name = "total_cost")
    private Long totalCost;
    @Basic(optional = false)
    @Column(name = "payment")
    private String payment;
    @Basic(optional = false)
    @Column(name = "active",columnDefinition ="boolean default false")
    private Boolean active;
    @Basic(optional = false)
    @Column(name = "active_evaluate",columnDefinition ="boolean default false")
    private boolean activeEvaluate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "importOrderID",fetch = FetchType.LAZY)
    private Collection<Detailimportorder> detailimportorderCollection;
    @JoinColumn(name = "imported_by", referencedColumnName = "id")
    @ManyToOne
    private User importedBy;

    public Importorder() {
    }

    public Importorder(Integer id) {
        this.id = id;
    }

    public Importorder(Integer id, Date orderDate, String payment,Boolean active, Boolean activeEvaluate) {
        this.id = id;
        this.orderDate = orderDate;
        this.payment = payment;
        this.active = active;
        this.activeEvaluate = activeEvaluate;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setActiveEvaluate(boolean activeEvaluate) {
        this.activeEvaluate = activeEvaluate;
    }

    public Boolean getActive() {
        return active;
    }

    public boolean isActiveEvaluate() {
        return activeEvaluate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Collection<Detailimportorder> getDetailimportorderCollection() {
        return detailimportorderCollection;
    }

    public void setDetailimportorderCollection(Collection<Detailimportorder> detailimportorderCollection) {
        this.detailimportorderCollection = detailimportorderCollection;
    }

    public User getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(User importedBy) {
        this.importedBy = importedBy;
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
        if (!(object instanceof Importorder)) {
            return false;
        }
        Importorder other = (Importorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Importorder[ id=" + id + " ]";
    }
    
}

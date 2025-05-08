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
 * @author ADMIN
 */
@Entity
@Table(name = "oderexport")
@NamedQueries({
        @NamedQuery(name = "Oderexport.findAll", query = "SELECT o FROM Oderexport o"),
        @NamedQuery(name = "Oderexport.findById", query = "SELECT o FROM Oderexport o WHERE o.id = :id"),
        @NamedQuery(name = "Oderexport.findByTypeBuy", query = "SELECT o FROM Oderexport o WHERE o.typeBuy = :typeBuy"),
        @NamedQuery(name = "Oderexport.findByState", query = "SELECT o FROM Oderexport o WHERE o.state = :state"),
        @NamedQuery(name = "Oderexport.findByCreatedDate", query = "SELECT o FROM Oderexport o WHERE o.createdDate = :createdDate"),
        @NamedQuery(name = "Oderexport.findByTotalPrice", query = "SELECT o FROM Oderexport o WHERE o.totalPrice = :totalPrice")})
public class Oderexport implements Serializable {
    public enum State {
        V1("Đang Xử Lý"),
        V2("Đang Vận Chuyển"),
        V4("Đã Hủy"),
        V5("Hoàn Thành");

        private String value;

        State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static State getStateOrDefault(String value) {
            for (State state : State.values()) {
                if (state.getValue().equalsIgnoreCase(value)) {
                    return state;
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
    @Column(name = "type_buy")
    private String typeBuy;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "total_price")
    private Long totalPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exportorderId")
    private Collection<Detailsexportordercost> detailsexportordercostCollection;
    @OneToOne(mappedBy = "oderid", cascade = CascadeType.ALL)
    private Shipment shipment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exportOrderId")
    private Collection<Detailexportorder> detailexportorderCollection;
    @JoinColumn(name = "order_by", referencedColumnName = "id")
    @ManyToOne
    private User order_by;
    @JoinColumn(name = "order_for", referencedColumnName = "id")
    @ManyToOne
    private Agency order_for;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Logistcs")
    private Collection<Logistics> logisticsCollection;

    public Oderexport() {
    }

    public Oderexport(Integer id) {
        this.id = id;
    }

    public Oderexport(Integer id, String typeBuy, String state, Date createdDate) {
        this.id = id;
        this.typeBuy = typeBuy;
        this.state = state;
        this.createdDate = createdDate;
    }

    public Collection<Logistics> getLogisticsCollection() {
        return logisticsCollection;
    }

    public void setLogisticsCollection(Collection<Logistics> logisticsCollection) {
        this.logisticsCollection = logisticsCollection;
    }

    public User getOrder_by() {
        return order_by;
    }

    public void setOrder_by(User order_by) {
        this.order_by = order_by;
    }

    public Agency getOrder_for() {
        return order_for;
    }

    public void setOrder_for(Agency order_for) {
        this.order_for = order_for;
    }


    public Collection<Detailexportorder> getDetailexportorderCollection() {
        return detailexportorderCollection;
    }

    public void setDetailexportorderCollection(Collection<Detailexportorder> detailexportorderCollection) {
        this.detailexportorderCollection = detailexportorderCollection;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeBuy() {
        return typeBuy;
    }

    public void setTypeBuy(String typeBuy) {
        this.typeBuy = typeBuy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getTotalPrice() {
        return totalPrice;
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
        if (!(object instanceof Oderexport)) {
            return false;
        }
        Oderexport other = (Oderexport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Oderexport[ id=" + id + " ]";
    }

}

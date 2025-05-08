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
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.Id = :Id")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
    private User useridE;
    @JoinColumn(name = "Position", referencedColumnName = "id")
    @ManyToOne
    private Warehouse position;

    public Employee() {
    }

    public Employee(Integer userId) {
        this.Id = userId;
    }

    public Employee(int userId) {
        this.Id = userId;
    }

    public Integer getUserId() {
        return Id;
    }

    public void setUserId(Integer userId) {
        this.Id = userId;
    }

    public User getUser() {
        return useridE;
    }

    public void setUser(User user) {
        this.useridE = user;
    }

    public Warehouse getPosition() {
        return position;
    }

    public void setPosition(Warehouse position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUseridE() {
        return useridE;
    }

    public void setUseridE(User useridE) {
        this.useridE = useridE;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.Employee[ userId=" + Id + " ]";
    }
    
}

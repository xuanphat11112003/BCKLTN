/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "User.findByCreatedDate", query = "SELECT u FROM User u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "User.findByAvatar", query = "SELECT u FROM User u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "User.findByAddress", query = "SELECT u FROM User u WHERE u.address = :address"),
    @NamedQuery(name = "User.findByUserRole", query = "SELECT u FROM User u WHERE u.userRole = :userRole"),
    @NamedQuery(name = "User.findByActive", query = "SELECT u FROM User u WHERE u.active = :active"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User implements Serializable {
    public enum UserRole{
        ROLE_ADMIN, ROLE_AGENCY, ROLE_USER, ROLE_EMPLOYEE;
        public static UserRole fromvalue(String value){
            for(UserRole userRole1 : UserRole.values()){
                if(userRole1.name().equalsIgnoreCase(value))
                    return userRole1;
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
    @Column(name = "username")
    @NotBlank(message = "Username is required")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @Basic(optional = false)
    @NotBlank(message = "First Name is required")
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotBlank(message = "Last Name is required")
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "avatar")
    private String avatar;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private Collection<Performanevaluation> performanevaluationCollection;
    @OneToMany(mappedBy = "importedBy", fetch = FetchType.LAZY)
    private Collection<Importorder> importorderCollection;
    @OneToMany(mappedBy = "order_by", fetch = FetchType.LAZY)
    private  Collection<OrderManufacture> orderManufactureCollection;
    @OneToMany(mappedBy = "order_by", fetch = FetchType.LAZY)
    private Collection<Oderexport> oderexportCollection;
    @Transient
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setOrderManufactureCollection(Collection<OrderManufacture> orderManufactureCollection) {
        this.orderManufactureCollection = orderManufactureCollection;
    }

    public boolean isActive() {
        return active;
    }

    public Collection<OrderManufacture> getOrderManufactureCollection() {
        return orderManufactureCollection;
    }

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username, String password, String firstname, String lastname, Date createdDate, String address, UserRole userRole, boolean active, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdDate = createdDate;
        this.address = address;
        this.userRole = userRole;
        this.active = active;
        this.email = email;
    }

    public Collection<Oderexport> getOderexportCollection() {
        return oderexportCollection;
    }

    public void setOderexportCollection(Collection<Oderexport> oderexportCollection) {
        this.oderexportCollection = oderexportCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Performanevaluation> getPerformanevaluationCollection() {
        return performanevaluationCollection;
    }

    public void setPerformanevaluationCollection(Collection<Performanevaluation> performanevaluationCollection) {
        this.performanevaluationCollection = performanevaluationCollection;
    }

    public Collection<Importorder> getImportorderCollection() {
        return importorderCollection;
    }

    public void setImportorderCollection(Collection<Importorder> importorderCollection) {
        this.importorderCollection = importorderCollection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dxp.HeThongChuoiCungUng.Model.User[ id=" + id + " ]";
    }
    
}

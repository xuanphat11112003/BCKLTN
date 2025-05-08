/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.DTO.Request;


import com.dxp.HeThongChuoiCungUng.Model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ADMIN
 */
@Getter
@Setter
@NoArgsConstructor
public class UserCreationRequest {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String useRole;
    private String email;
    private Boolean active;
    private String wareHouseName;
    private String wareHouseId;
    private String role;
    private String avatar;


    public UserCreationRequest(User u){
        this.username = u.getUsername();
        this.lastName = u.getLastname();
        this.firstName = u.getFirstname();
        this.active = u.getActive();
        this.address = u.getAddress();
        this.email = u.getEmail();
        this.role = u.getUserRole().toString();
        this.avatar = u.getAvatar();

    }
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the useRole
     */
    public String getUseRole() {
        return useRole;
    }

    /**
     * @param useRole the useRole to set
     */
    public void setUseRole(String useRole) {
        this.useRole = useRole;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    
}

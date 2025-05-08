/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.DTO.Request.UserCreationRequest;
import com.dxp.HeThongChuoiCungUng.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 * @author ADMIN
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findById(int id);
    List<User> findByUserRole(User.UserRole userRole);
}

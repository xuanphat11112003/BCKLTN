/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.UserCreationRequest;
import com.dxp.HeThongChuoiCungUng.Model.Employee;
import com.dxp.HeThongChuoiCungUng.Model.Manager;
import com.dxp.HeThongChuoiCungUng.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */

    public interface UserService extends UserDetailsService {
        User getUser(String username);
        User createUserRequest(UserCreationRequest request);
        List<User> getAllUser();
        User LoadUserByName(String name);
        void save(User user);
        User findById(int id);
        void saveEmployee(Employee employee);
        void saveManager(Manager manager);
        List<User> findAllUser(Map<String,String> params);
        Boolean authUser(String userName, String password);
        public void Delete(int id);
        public Employee getEmployeeByUserId(int id);

    }

package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dxp.HeThongChuoiCungUng.DTO.Request.UserCreationRequest;
import com.dxp.HeThongChuoiCungUng.Model.Employee;
import com.dxp.HeThongChuoiCungUng.Model.Manager;
import com.dxp.HeThongChuoiCungUng.Model.User;
import com.dxp.HeThongChuoiCungUng.Repository.AgencyRepository;
import com.dxp.HeThongChuoiCungUng.Repository.EmployeeRepository;
import com.dxp.HeThongChuoiCungUng.Repository.ManagerRepository;
import com.dxp.HeThongChuoiCungUng.Repository.UserRepository;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AgencyRepository agencyRepository;


    @Override
    public User getUser(String username) {
        return this.userRepo.findByUsername(username);
    }
    @Override

    public List<User> getAllUser(){
        return this.userRepo.findAll();
    }

    @Override
    public User LoadUserByName(String name) {
        return this.userRepo.findByUsername(name);
    }

    @Override
    public void save(User user) {
        if (!user.getFile().isEmpty()){
            try {
                Map res =  this.cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Date date = new Date();
        user.setCreatedDate(date);
        this.userRepo.save(user);

    }

    @Override
    public User findById(int id) {
        return this.userRepo.findById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        User user = new User();
        if (!employee.getUser().getFile().isEmpty()){
            try {
                Map res =  this.cloudinary.uploader().upload(employee.getUser().getFile().getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        user.setUserRole(User.UserRole.ROLE_EMPLOYEE);
        user.setFirstname(employee.getUser().getFirstname());
        user.setLastname(employee.getUser().getLastname());
        user.setAddress(employee.getUser().getAddress());
        user.setUsername(employee.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(employee.getUser().getPassword()));
        user.setActive(false);
        user.setEmail(employee.getUser().getEmail());
        Date date = new Date();
        user.setCreatedDate(date);
        this.userRepo.save(user);
        Employee employee1 = new Employee();
        employee1.setUser(user);
        employee1.setPosition(employee.getPosition());
        this.employeeRepository.save(employee1);
    }

    @Override
    public void saveManager(Manager manager) {
        User user = new User();
        if (!manager.getUser().getFile().isEmpty()){
            try {
                Map res =  this.cloudinary.uploader().upload(manager.getUser().getFile().getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        user.setUserRole(User.UserRole.ROLE_USER);
        user.setFirstname(manager.getUser().getFirstname());
        user.setLastname(manager.getUser().getLastname());
        user.setAddress(manager.getUser().getAddress());
        user.setUsername(manager.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(manager.getUser().getPassword()));
        user.setActive(false);
        user.setEmail(manager.getUser().getEmail());
        Date date = new Date();
        user.setCreatedDate(date);
        this.userRepo.save(user);
        Manager manager1 = new Manager();
        manager1.setUser(user);
        manager1.setCreatedate(manager.getCreatedate());
        manager1.setEndDate(manager.getEndDate());
        this.managerRepository.save(manager1);
    }

    @Override
    public List<User> findAllUser(Map<String, String> params) {
        if(!params.isEmpty()){
            List<User> users = new ArrayList<>();
            if(!params.get("kw").isEmpty()){
                int id = Integer.parseInt(params.get("kw"));
                User user = this.userRepo.findById(id);
                if (user != null) {
                    users.add(user);
                }
            }
            if(!params.get("useRole").isEmpty()){
                users.addAll(this.userRepo.findByUserRole(User.UserRole.fromvalue(params.get("useRole").toString())));
            }
            if(!params.get("userName").isEmpty()){
                User user = this.userRepo.findByUsername(params.get("userName").toString());
                if(user != null){
                    users.add(user);
                }
            }
            return users;
        }else {
            return this.userRepo.findAll();
        }
    }

    @Override
    public Boolean authUser(String userName, String password) {
        User u = this.LoadUserByName(userName);
        return this.passwordEncoder.matches(password,u.getPassword());
    }

    @Override
    public void Delete(int id) {
        this.userRepo.delete(this.findById(id));
    }

    @Override
    public Employee getEmployeeByUserId(int id) {
        return this.employeeRepository.findByUseridE_Id(id);
    }

    @Override
    public User createUserRequest(UserCreationRequest request) {
        User user = new User();
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        user.setUserRole(User.UserRole.fromvalue(request.getUseRole()));
        user.setActive(request.getActive());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        return userRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepo.findByUsername(username);
        if(u == null){
            throw new UsernameNotFoundException("User Not Found");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(u.getUsername(),u.getPassword(),authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Trả về danh sách quyền của người dùng
        return Arrays.stream(user.getUserRole().values()).toList().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }
}

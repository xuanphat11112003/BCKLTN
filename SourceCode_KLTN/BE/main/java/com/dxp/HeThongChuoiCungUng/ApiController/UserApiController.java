/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.Component.JwtTokenProvider;
import com.dxp.HeThongChuoiCungUng.DTO.Request.UserCreationRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.UserTokenReponse;
import com.dxp.HeThongChuoiCungUng.Model.User;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
//import com.dxp.HeThongChuoiCungUng.ServiceImpl.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@RestController
public class UserApiController {
    @Autowired
    private UserService userSevice;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/users/{username}")
    public User getUser(@PathVariable String username){
        return userSevice.getUser(username);
    }
    @PostMapping("/users")
    public User createUser(@RequestBody UserCreationRequest reuqest){
        return this.userSevice.createUserRequest(reuqest);
    }


    @GetMapping("/users")
    public List<User> getAllUser(){
        return this.userSevice.getAllUser();
    }



    @PostMapping("/api/auth/login")
    @CrossOrigin
    public ResponseEntity<?> login (@RequestBody UserCreationRequest userCreationRequest){
       try{
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(userCreationRequest.getUsername(),
                           userCreationRequest.getPassword())
           );
           SecurityContextHolder.getContext().setAuthentication(authentication);
           String jwt = jwtTokenProvider.createToken(authentication);
           return ResponseEntity.ok(new UserTokenReponse(jwt));

       } catch (Exception e) {
           return new ResponseEntity<>("unAuthentication", HttpStatus.BAD_REQUEST);
       }
    }
    @PostMapping("/api/users/me")
    @CrossOrigin
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        String username = principal.getName();
        User u = this.userSevice.LoadUserByName(username);
        UserCreationRequest userCreationRequest = new UserCreationRequest(u);
        return ResponseEntity.ok(userCreationRequest);
    }



    //    @Autowired
//    private RedisService redisService;
//    @PostMapping("/save")
//    public String saveToRedis(@RequestParam String key, @RequestParam String value) {
//        redisService.saveToRedis(key, value);
//        return "Data saved to Redis with key: " + key;
//    }
//
//    // Endpoint để lấy dữ liệu từ Redis
//    @GetMapping("/get")
//    public Object getFromRedis(@RequestParam String key) {
//        return redisService.getFromRedis(key);
//    }

}

package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Employee;
import com.dxp.HeThongChuoiCungUng.Model.Manager;
import com.dxp.HeThongChuoiCungUng.Model.User;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WareHouseService wareHouseService;
    @GetMapping("user/index")
    public String indexUser(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("users",this.userService.findAllUser(params));
        model.addAttribute("userRoles", User.UserRole.values());
        return "user/index";
    }

    @GetMapping("/user")
    public String addUser(Model model){
        model.addAttribute("employee",new Employee());
        model.addAttribute("manager",new Manager());
        model.addAttribute("warehouses",wareHouseService.findAll());
        return "user/add-user";
    }
    @PostMapping("/employee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("warehouses", wareHouseService.findAll());
            return "user/add-user";
        }
        try {
            userService.saveEmployee(user);
        }catch (Exception ex){
            model.addAttribute("errMsg", ex.getMessage());
            model.addAt
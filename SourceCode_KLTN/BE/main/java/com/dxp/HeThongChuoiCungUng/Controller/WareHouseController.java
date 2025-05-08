package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Service.OrderExportService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private OrderExportService orderExportService;

    @GetMapping("/warehouse")
    public String indexWareHouse (Model model, @RequestParam Map<String,String> params){
        model.addAttribute("wareHouses",this.wareHouseService.findAll());
        return "/warehouse/index";
    }
    @RequestMapping("/warehouse/add")
    public String indexAddWareHouse(Model model){
        model.addAttribute("warehouse",new Warehouse());
        return "/warehouse/AddWareHouse";
    }
    @PostMapping("/warehouse/add")
    public String addWareHouse(@Valid @ModelAttribute("warehouse") Warehouse warehouse,
                               BindingResult result, Model model){
        if(result.hasErrors()){
            return "/warehouse/AddWareHouse";
        }
        try{
            this.wareHouseService.save(warehouse);
        } catch (Exception ex) {
            model.addAttribute("errMsg", ex.getMessage());
            return "/warehouse/AddWareHouse";
        }
        return "redirect:/warehouse";
    }

    @GetMapping("/warehouse/{id}")
    public String seeOrderInWareHouse(@PathVariable(name = "id")int id , Model model ,@RequestParam Map<String, String>params){
        model.addAttribute("warehouse", this.wareHouseService.findOrderInWareHouse(id,params));
        model.addAttribute("wid", id);
        model.addAttribute("warehouseChoice", this.wareHouseService.findAll());
        return "warehouse/see-orders";
    }
}

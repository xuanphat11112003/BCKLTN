package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Cost;
import com.dxp.HeThongChuoiCungUng.Service.CostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CostController {
    @Autowired
    private CostService costService;
    @GetMapping("/cost")
    public String indexCost(Model model){
        model.addAttribute("costs", this.costService.findByAll());
        return "cost/index";
    }
    @GetMapping("/cost/add")
    public String indexAddCost(Model model){
        model.addAttribute("costs", new Cost());
        return "cost/add-cost";
    }
    @PostMapping("/cost/add")
    public String addCost(@Valid @ModelAttribute("costs") Cost cost, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "cost/add-cost";
        }
        try {
            this.costService.save(cost);
            return "redirect:/cost";
        } catch (Exception e) {
            model.addAttribute("errg", e.getMessage());
            return "cost/add-cost";
        }
    }

}

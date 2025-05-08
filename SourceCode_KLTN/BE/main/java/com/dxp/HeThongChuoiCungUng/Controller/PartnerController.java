package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Partner;
import com.dxp.HeThongChuoiCungUng.Service.PartnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PartnerController {
    @Autowired
    private PartnerService partnerService;

    @GetMapping("/partners/add")
    public String indexaddAgency(Model model){
        model.addAttribute("partner", new Partner());
        return "partner/add-agency";
    }
    @PostMapping("/partners/add")
    public String addAgency(@Valid @ModelAttribute ("partner")Partner partner,  BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            return "partner/add-agency";
        }
        try{
            this.partnerService.save(partner);
            return  "redirect:/partners/add";
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            return "partner/add-agency";
        }

    }
    @GetMapping("/partners")
    public String indexPartner(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("partners", this.partnerService.getAll(params));
        return "partner/index";
    }
    @GetMapping("/contract/new/{id}")
    public String indexAddContract (Model model, @PathVariable(value = "id") int id){
        model.addAttribute("partners", new Partner());
        Partner partner = this.partnerService.findById(id);
        model.addAttribute("part",partner);
        int ou = 0;
        if(Partner.Type.AGENCY.equals(partner.getType()))
            ou = partner.getPartnerAgency().getAgency().getId();
        else
            ou = partner.getPartnerTransport().getPartner_trans().getId();
        model.addAttribute("ou",ou);
        return "partner/add-contract";
    }
    @PostMapping("/contract/new/{id}")
    public String addContract(@Valid @ModelAttribute("partners")Partner partner, Model model, BindingResult bindingResult,
    @PathVariable(value = "id") int id){
        if (bindingResult.hasErrors()) {
            model.addAttribute("part",this.partnerService.findById(id));
            return "partner/add-contract";
        }
        try {
            this.partnerService.addContract(partner,id);
            return  "redirect:/partners";
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            model.addAttribute("part",this.partnerService.findById(id));
            return "partner/add-contract";
        }
    }
}

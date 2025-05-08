package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MaterialController {
    @Autowired
    private MaterialService maService ;
    @Autowired
    private CatergoryService catService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/material")
    public String indexMaterial(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("materials",this.maService.getALlMaterial(params));
        model.addAttribute("categories",this.catService.findByType("material"));
        return "/material/index";
    }
    @GetMapping("/AddMaterial")
    public String indexAddMaterial(Model model){
        model.addAttribute("material",new Material());
        List<Category> categories = this.catService.findByType("material");
        model.addAttribute("categories",categories);
        model.addAttribute("supplier",this.supplierService.getAllSupplier());
        return "material/add-material";
    }
    @PostMapping("/AddMaterial")
    public String AddNewMaterial(Model model, @Valid @ModelAttribute("material")Material material, BindingResult result){
        List<Category> categories = this.catService.findByType("material");
        model.addAttribute("categories",categories);
        model.addAttribute("supplier",this.supplierService.getAllSupplier());
        if(result.hasErrors()){
            return "material/AddMaterial";
        }
        try{
            this.maService.save(material);
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            return "product/add-product";
        }
        return "redirect:/material";

    }
}

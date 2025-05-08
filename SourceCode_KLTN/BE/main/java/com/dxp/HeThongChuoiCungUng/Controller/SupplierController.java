package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Supplier;
import com.dxp.HeThongChuoiCungUng.Model.Supplierperformance;
import com.dxp.HeThongChuoiCungUng.Service.SupplierPerformanceService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {
    @Autowired
    private SupplierService supService;
    @Autowired
    private SupplierPerformanceService supplierPerformanceService;

    @RequestMapping("/supplier")
    public String indexSupplier(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("suppliers",this.supService.getAllSupplier(params));
        return "/supplier/index";
    }
    @GetMapping("/add-supplier")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/add-supplier";
    }

    @PostMapping("/add-supplier")
    public String addSupplier(@Valid @ModelAttribute("supplier") Supplier supplier,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "supplier/add-supplier";
        }
        supService.save(supplier);
        model.addAttribute("successMessage", "Nhà cung cấp đã được thêm thành công!");
        return "redirect:/supplier";
    }
    @GetMapping("supplier/add-evaluate/{orderId}/{supplierId}")
    public String addEvaluateSupplier(Model model ,@PathVariable(value = "orderId") int orderID,
                                      @PathVariable(value = "supplierId") int supplierId){
        model.addAttribute("oid",orderID);
        model.addAttribute("sid",supplierId);
        return "supplier/add-evaluate";
    }
    @GetMapping("supplier-performance/{supplierId}")
    public String getPerformanceBySupplierId(@PathVariable Integer supplierId, Model model) {
        model.addAttribute("supplierPerformanceList",supplierPerformanceService.getPerformanceBySupplierId(supplierId));
        return "supplier/performance";
    }

}

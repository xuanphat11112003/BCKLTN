package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.Model.Importorder;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ImportOrderController {
    @Autowired
    private CatergoryService catergoryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ImportOrderService importOrderService;
    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping("/import-order")
    public String AddImportOrder(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("materials",materialService.getALlMaterial(params));
        model.addAttribute("categories",catergoryService.findByType("material"));
        model.addAttribute("suppliers",supplierService.getAllSupplier());
        model.addAttribute("payments", Importorder.Payment.values());
        return "import-order/add-import-order";
    }
    @GetMapping("/indexImportOrder")
    public String indexImportOrder(Model model, @RequestParam Map<String,String> params){
        List<ImportorderCreatetionRequest> importorders = this.importOrderService.findImportOrder(params);
        List<Warehouse> warehouses = this.wareHouseService.findAll();
        model.addAttribute("orders", importorders);
        model.addAttribute("warehouse", warehouses);
        return "import-order/index";
    }
}

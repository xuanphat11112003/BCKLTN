package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class OrderExportController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CatergoryService catergoryService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private CostService costService;
    @Autowired
    private OrderExportService orderExportService;
    @Autowired
    private TransportPartnerService transportPartnerService;

    @GetMapping("/orderExport")
    public String OrderExport(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("products",this.productService.getAllProduct(params));
        model.addAttribute("categories",this.catergoryService.findByType("product"));
        model.addAttribute("agencies",this.agencyService.findAll());
        model.addAttribute("costTrans", this.costService.findByType("Transport"));
        return "export-order/add-order";
    }

    @GetMapping("/orderExport/index")
    public String indexOrderExport(Model model, @RequestParam Map<String,String> parans){
        model.addAttribute("orderExportRequest", this.orderExportService.getAllOrderExport(parans));
        model.addAttribute("transport", this.transportPartnerService.findAll());
        return "export-order/index";
    }
}

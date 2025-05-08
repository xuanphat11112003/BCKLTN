package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Service.OrderDetailManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.StatsService;
import com.dxp.HeThongChuoiCungUng.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class StatsController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private OrderDetailManufactureService orderDetailManufactureService;

    @GetMapping("/stats")
    public String StatsSupplier(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("supplierStats", supplierService.getStats(params.get("name")));
        return "stats/index";
    }
    @GetMapping("/report/monthly")
    public String showMonthlyReport(@RequestParam Map<String, String> params, Model model) {
        int month = Integer.parseInt(params.get("month"));
        int year = Integer.parseInt(params.get("year"));
        Map<String, Object> report = statsService.getMonthlyReport(month, year);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("weeklyCosts", report.get("WeeklyCosts"));
        model.addAttribute("weeklyRevenues", report.get("WeeklyRevenue"));
        return "stats/orderExport";
    }
    @GetMapping("/report")
    public String getReport(@RequestParam(required = false) Integer year, Model model) {
        if (year == null) {
            year = 2024;
        }
        model.addAttribute("stats", statsService.getMonthlyReport(year));
        return "stats/indexOrderExport";
    }
    @GetMapping("/manufactureReport")
    public String showProductProductionReport(Model model) {
        List<ProductRequest> productProductionData = orderDetailManufactureService.getProductProductionData();
        model.addAttribute("productProductionData", productProductionData);
        return "stats/manufacture";
    }
    @GetMapping("/indexStats")
    public String index(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("supplierStats", supplierService.getStats(params.get("name")));
        model.addAttribute("stats", statsService.getMonthlyReport(2024));
        List<ProductRequest> productProductionData = orderDetailManufactureService.getProductProductionData();
        model.addAttribute("productProductionData", productProductionData);
        return "stats/indextotal";
    }
}

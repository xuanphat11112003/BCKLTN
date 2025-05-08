package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderManufactureRequest;
import com.dxp.HeThongChuoiCungUng.Model.Importorder;
import com.dxp.HeThongChuoiCungUng.Model.OrderManufacture;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import com.dxp.HeThongChuoiCungUng.Service.OrderManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.ProductService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class OrderManufactureController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CatergoryService catergoryService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private OrderManufactureService orderManufactureService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/manufacture/order")
    public String OrderManufacture(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("products",this.productService.getAllProduct(params));
        model.addAttribute("categories",this.catergoryService.findByType("product"));
        model.addAttribute("warehouses",this.wareHouseService.findAll());
        model.addAttribute("payments", Importorder.Payment.values());
        return "manufacture/order-manufacture";
    }
    @GetMapping("/orderManufacture")
    public String indexOrderManufacture(Model model, @RequestParam Map<String,String> params) throws JsonProcessingException {
        List<OrderManufactureRequest> orderList = this.orderManufactureService.findAll(params);
        model.addAttribute("orderList", orderList);
        model.addAttribute("transactions", OrderManufacture.transactions.values());
        String jsonOrderList = objectMapper.writeValueAsString(orderList);
        model.addAttribute("jsonOrderList", jsonOrderList);
        return "manufacture/index-orderManufacture";
    }
}

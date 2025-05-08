package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import com.dxp.HeThongChuoiCungUng.Service.ManufactureService;
import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
import com.dxp.HeThongChuoiCungUng.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ManufactureController {
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CatergoryService catergoryService;

    @GetMapping("/manufacture/index")
    public String IndexManufacture(Model model, @RequestParam Map<String,String> params){
        List<Manufacturerequest> manufacturerequests = this.manufactureService.getAllManu(params);
        Map<String,List<Map<String,Object>>> groupMap = new HashMap<>();
        for(Manufacturerequest manufacturerequest : manufacturerequests){
            String name = manufacturerequest.getProductName();
            Map<String, Object> details = new HashMap<>();
            details.put("material", manufacturerequest.getMaterialName());
            details.put("amount",manufacturerequest.getAmount());
            details.put("unit",manufacturerequest.getUnit());
            if(!groupMap.containsKey(name)){
                List<Map<String,Object>> detailList = new ArrayList<>();
                groupMap.put(name,detailList);
            }
            groupMap.get(name).add(details);
        }
        model.addAttribute("groupMaps",groupMap);
        return "/manufacture/index";
    }
    @GetMapping("/manufacture/add")
    public String AddManufacture(Model model,@RequestParam Map<String,String> params){
        List<Integer> productIds = manufactureService.getAllManu().stream()
                .map(Manufacturerequest::getProductId)
                .collect(Collectors.toList());
        model.addAttribute("manufactures",productIds);
        model.addAttribute("products",productService.getAllProduct());
        model.addAttribute("materials",materialService.getALlMaterial(params));
        model.addAttribute("categories",catergoryService.findByType("material"));
        return "manufacture/add-manufacture";
    }
}

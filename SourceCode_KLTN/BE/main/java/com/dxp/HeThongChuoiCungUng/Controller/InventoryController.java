package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialInventoryRequest;
import com.dxp.HeThongChuoiCungUng.Service.InventoryService;
import com.dxp.HeThongChuoiCungUng.Service.WareHouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class InventoryController {
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private InventoryService inventoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/inventory")
    public String indexInventory(Model model){
        model.addAttribute("warehouses",this.wareHouseService.findAll());
        return "inventory/index";
    }
    @GetMapping("/inventory/view/{warehouseId}")
    public String viewInventory(@RequestParam Map<String, String> params, @PathVariable("warehouseId") int warehouseId, Model model) throws JsonProcessingException {

        List<MaterialInventoryRequest> inventoryRequests = inventoryService.getInventory(params, warehouseId);
        String stockType = params.get("stockType");
        model.addAttribute("inventoryRequests", inventoryRequests);
        model.addAttribute("warehouseId", warehouseId);
        model.addAttribute("stockType", stockType);
        String list =  objectMapper.writeValueAsString(inventoryRequests);
        String jsonOrderList = objectMapper.writeValueAsString(list);
        model.addAttribute("jsonOrderList", jsonOrderList);
        return "inventory/view-stock";
    }
    @GetMapping("updateDateExpire/{id}")
    public String updateDateExpire(@PathVariable (value = "id") int id, Model model){
        MaterialInventoryRequest inventoryRequest = inventoryService.findById(id);
        model.addAttribute("inventory",inventoryRequest);
        return "inventory/update-dateExpire";
    }
}

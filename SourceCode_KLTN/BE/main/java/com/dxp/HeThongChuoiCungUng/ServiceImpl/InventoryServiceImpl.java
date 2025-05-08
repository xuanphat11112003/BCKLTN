package com.dxp.HeThongChuoiCungUng.ServiceImpl;
import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialInventoryRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialStockRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductStockRequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Materialstock;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import com.dxp.HeThongChuoiCungUng.Repository.InventoryRepository;
import com.dxp.HeThongChuoiCungUng.Service.InventoryService;
import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
import com.dxp.HeThongChuoiCungUng.Specification.InventorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private MaterialService materialService;

    @Override
    public void save(Inventory inv) {
        this.inventoryRepository.save(inv);
    }

    @Override
    public List<MaterialInventoryRequest> findAll(Map<String,String> paramns) {
        Specification<Inventory> inventorySpecification = InventorySpecification.getAllAsc(paramns);
        List<Inventory> inventories = this.inventoryRepository.findAll(inventorySpecification);

        return inventories.stream().map(this::converToDTO).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, MaterialStockRequest> checkMaterialAvailable(List<Manufacturerequest> manufacturerequests) {
        Map<Integer, MaterialStockRequest> insufficientMaterials = new HashMap<>();
        for(Manufacturerequest manuDTO : manufacturerequests){
            int quantityNeed = manuDTO.getAmount();
            int materialId = manuDTO.getMaterialId();
            Map<String,String> params = new HashMap<>();
            params.put("materialId",String.valueOf(materialId));
            List<MaterialInventoryRequest> materialInventoryRequests = this.findAll(params);
            int currentQuantity = 0;
            for (MaterialInventoryRequest materialDTO : materialInventoryRequests){

                for(MaterialStockRequest materialStockRequest : materialDTO.getMaterialStock()){
                    if(materialStockRequest.getMaterialId() == materialId)
                        currentQuantity += materialStockRequest.getAmount();
                }
            }
            if(quantityNeed > currentQuantity){
                Material material = this.materialService.findById(materialId);
                int quantity = quantityNeed - currentQuantity;
                MaterialStockRequest materialStockRequest = new MaterialStockRequest();
                materialStockRequest.setMaterialName(material.getName());
                materialStockRequest.setSupplierName(material.getSupplierId().getName());
                materialStockRequest.setSupplierId(material.getSupplierId().getId());
                materialStockRequest.setMaterialId(material.getId());
                materialStockRequest.setAmount(quantity);
                materialStockRequest.setRemain(manuDTO.getWeight());
                insufficientMaterials.put(materialId,materialStockRequest);
            }
        }
        return insufficientMaterials;
    }

    @Override
    public Map<Integer, MaterialStockRequest> checkRemainMaterialAvailable(List<Manufacturerequest> manufacturerequests) {
        Map<Integer, MaterialStockRequest> insufficientMaterials = new HashMap<>();
        for(Manufacturerequest manuDTO : manufacturerequests){
            int quantityNeed = manuDTO.getAmount();
            int materialId = manuDTO.getMaterialId();
            Map<String,String> params = new HashMap<>();
            params.put("materialId",String.valueOf(materialId));
            List<MaterialInventoryRequest> materialInventoryRequests = this.findAll(params);
            int currentQuantity = 0;
            for (MaterialInventoryRequest materialDTO : materialInventoryRequests){

                for(MaterialStockRequest materialStockRequest : materialDTO.getMaterialStock()){
                    if(materialStockRequest.getMaterialId() == materialId)
                        if(materialStockRequest.isActive())
                            currentQuantity += materialStockRequest.getAmount();
                        else
                            currentQuantity += materialStockRequest.getRemain();
                }
            }
            if(quantityNeed > currentQuantity){
                Material material = this.materialService.findById(materialId);
                int quantity = quantityNeed - currentQuantity;
                MaterialStockRequest materialStockRequest = new MaterialStockRequest();
                materialStockRequest.setMaterialName(material.getName());
                materialStockRequest.setSupplierName(material.getSupplierId().getName());
                materialStockRequest.setSupplierId(material.getSupplierId().getId());
                materialStockRequest.setMaterialId(material.getId());
                materialStockRequest.setAmount(quantity);
                materialStockRequest.setRemain(manuDTO.getWeight());
                insufficientMaterials.put(materialId,materialStockRequest);
            }
        }
        return insufficientMaterials;
    }

    @Override
    public List<MaterialInventoryRequest> getInventory(Map<String,String> params, int id) {
        Specification<Inventory> specification = InventorySpecification.searchByStockType(params, id);
        return this.inventoryRepository.findAll(specification).stream().map(this::converToDTO).collect(Collectors.toList());

    }

    @Override
    public MaterialInventoryRequest findById(int id) {
        return converToDTO(this.inventoryRepository.findById(id));
    }

    @Override
    public Inventory getById(int id) {
        return this.inventoryRepository.findById(id);
    }

    @Override
    public void Delete(int id) {
        this.inventoryRepository.delete(this.getById(id));
    }

    private MaterialInventoryRequest converToDTO(Inventory inventory){
        MaterialInventoryRequest materialInventoryRequest= new MaterialInventoryRequest();
        materialInventoryRequest.setId(inventory.getId());
        materialInventoryRequest.setEntryDate(inventory.getEntryDate());
        materialInventoryRequest.setWareHouseId(inventory.getWarehouse().getId());
        materialInventoryRequest.setWareHouseName(inventory.getWarehouse().getName());
        if(!inventory.getMaterialstockCollection().isEmpty()){
            List<MaterialStockRequest> materialStockRequests = inventory.getMaterialstockCollection().stream()
                    .map(this::convertToMaterialStockDTO).collect(Collectors.toList());
            materialInventoryRequest.setMaterialStock(materialStockRequests);
        }else if(!inventory.getProductstockCollection().isEmpty()){
            List<ProductStockRequest> productStockRequests = inventory.getProductstockCollection().stream()
                    .map(this::convertToProductStockDTO).collect(Collectors.toList());
            materialInventoryRequest.setProductStockRequests(productStockRequests);
        }

        return materialInventoryRequest;
    }
    private MaterialStockRequest convertToMaterialStockDTO(Materialstock materialstock){
        MaterialStockRequest materialStockRequest = new MaterialStockRequest();
        materialStockRequest.setAmount(materialstock.getAmount());
        materialStockRequest.setMaterialId(materialstock.getMaterialId().getId());
        materialStockRequest.setMaterialName(materialstock.getMaterialId().getName());
        materialStockRequest.setId(materialstock.getId());
        materialStockRequest.setDate_expire(materialstock.getDateExpiry());
        materialStockRequest.setActive(materialstock.getActive());
        materialStockRequest.setRemain(materialstock.getRemain());
        return materialStockRequest;
    }
    private ProductStockRequest convertToProductStockDTO(Productstock productstock){
        ProductStockRequest productStockRequest = new ProductStockRequest();
        productStockRequest.setProductId(productstock.getProductId().getId());
        productStockRequest.setProductName(productstock.getProductId().getName());
        productStockRequest.setId(productstock.getId());
        productStockRequest.setAmount(productstock.getAmount());
        productStockRequest.setActive(productstock.getActive());
        return productStockRequest;
    }
}

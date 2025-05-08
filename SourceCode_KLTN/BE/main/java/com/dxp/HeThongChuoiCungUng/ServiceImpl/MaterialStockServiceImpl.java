package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Materialstock;
import com.dxp.HeThongChuoiCungUng.Repository.InventoryRepository;
import com.dxp.HeThongChuoiCungUng.Repository.MaterialStockRepository;
import com.dxp.HeThongChuoiCungUng.Service.MaterialStockService;
import com.dxp.HeThongChuoiCungUng.Specification.InventorySpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MaterialStockServiceImpl implements MaterialStockService {
    @Autowired
    private MaterialStockRepository materialStockRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Override
    public void save(Materialstock material) {
        this.materialStockRepository.save(material);
    }

    @Override
    public void updateQuantity(List<Manufacturerequest> manufacturerequests) {
        for(Manufacturerequest manufacturerequest : manufacturerequests){
            int materialID = manufacturerequest.getMaterialId();
            int quantity = manufacturerequest.getAmount();
            Map<String,String> params = new HashMap<>();
            List<Materialstock> materialstocks = this.materialStockRepository.findByIdOrderByInventoryEntryDateAsc(materialID);
            for(Materialstock materialstock : materialstocks){
                if(materialstock.getActive()){
                    int currentQuantity = materialstock.getAmount();
                    if(currentQuantity >= quantity){
                        materialstock.setAmount(currentQuantity-quantity);
                        int so = materialstock.getAmount();
                        if(so == 0){
                            this.delete(materialstock);
                        }else
                            this.save(materialstock);
                        break;
                    }else {
                        quantity -= currentQuantity;
                        this.delete(materialstock);
                    }
                }else {
                    int currentQuantity = materialstock.getRemain();
                    if (currentQuantity >= quantity){
                        materialstock.setRemain(currentQuantity - quantity);
                        int so = materialstock.getAmount();
                        int so1 = materialstock.getRemain();
                        if(so1 == 0){
                            materialstock.setActive(true);
                            if(so == 0)
                                this.delete(materialstock);
                            else {
                                this.save(materialstock);
                            }
                        }
                        break;
                    }else{
                        quantity -= currentQuantity;
                        materialstock.setRemain(0);
                        this.delete(materialstock);

                    }
                }
            }

        }
    }

    @Override
    @Transactional
    public void delete(Materialstock materialstock) {
        if(materialstock.getId() != null){
            Inventory inventory = materialstock.getInventoryId();
            Long count = this.materialStockRepository.countByInventory(inventory);
            if(count == 0 ){
                this.inventoryRepository.delete(inventory);
            }
            this.materialStockRepository.delete(materialstock);
            this.materialStockRepository.flush();
        }
    }

    @Override
    public void updataActice(List<Manufacturerequest> manufacturerequests) {
        for(Manufacturerequest manufacturerequest : manufacturerequests){
            int materialId = manufacturerequest.getMaterialId();
            int quantity = manufacturerequest.getAmount();
            Map<String,String> params = new HashMap<>();
            List<Materialstock> materialstocks = this.materialStockRepository.findByIdOrderByInventoryEntryDateAsc(materialId);
            for(Materialstock materialstock : materialstocks){
                if(materialId == materialstock.getMaterialId().getId()){
                    if(materialstock.getActive()){
                        materialstock.setActive(false);
                        int quantityUpdate = materialstock.getAmount() -quantity;
                        if(quantityUpdate > 0 ){
                            materialstock.setAmount(quantityUpdate);
                            materialstock.setRemain(quantity);
                            break;
                        }else {
                            materialstock.setRemain(materialstock.getAmount());
                            materialstock.setAmount(0);
                        }
                    }else {
                        int quantityUpdate = materialstock.getAmount() - quantity;
                        int so = materialstock.getRemain();
                        if(quantityUpdate > 0){
                            materialstock.setRemain(so + quantity);
                            materialstock.setAmount(quantityUpdate);
                            break;
                        }else {
                            materialstock.setRemain(so + materialstock.getAmount());
                            materialstock.setAmount(0);
                        }
                    }
                }
                this.save(materialstock);
            }
        }

    }

    @Override
    public void restoreQuantity(List<Manufacturerequest> manufacturerequests) {
        for(Manufacturerequest manufacturerequest : manufacturerequests){
            int materialId = manufacturerequest.getMaterialId();
            int quantity = manufacturerequest.getAmount();
            List<Materialstock> materialstocks = this.materialStockRepository.findByIdOrderByInventoryEntryDateAsc(materialId);
            for (Materialstock materialstock : materialstocks){
                if(!materialstock.getActive()){
                    int currentQuantity = materialstock.getRemain();
                    int Amount = materialstock.getAmount();
                    if(currentQuantity >= quantity){
                        int so = currentQuantity - quantity;
                        if(so > 0){
                            materialstock.setRemain(so);
                            materialstock.setAmount(Amount + quantity);
                        }else {
                            materialstock.setRemain(0);
                            materialstock.setAmount(Amount+currentQuantity);
                            materialstock.setActive(true);
                        }
                        this.save(materialstock);
                        break;
                    }else {
                        materialstock.setRemain(0);
                        materialstock.setAmount(Amount+currentQuantity);
                        materialstock.setActive(true);
                        this.save(materialstock);
                    }
                }
            }
        }
    }

    @Override
    public Materialstock findById(int id) {
        return this.materialStockRepository.findById(id);
    }
}

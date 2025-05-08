package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailOrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderExportRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductStockRequest;
import com.dxp.HeThongChuoiCungUng.Model.Inventory;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Model.Productstock;
import com.dxp.HeThongChuoiCungUng.Model.Warehouse;
import com.dxp.HeThongChuoiCungUng.Repository.InventoryRepository;
import com.dxp.HeThongChuoiCungUng.Repository.ProductRepository;
import com.dxp.HeThongChuoiCungUng.Repository.ProductStockRepository;
import com.dxp.HeThongChuoiCungUng.Service.ProductStockService;
import com.dxp.HeThongChuoiCungUng.Specification.InventorySpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductStockServiceImpl implements ProductStockService {
    @Autowired
    private ProductStockRepository productStockRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public void save(Productstock productstock) {
        this.productStockRepository.save(productstock);
    }

    @Override
    public int checkwareHouse(List<Map<String, Integer>> params) {
        Set<Integer> warehouseIds = new HashSet<>();
        Map<Integer, Integer> productRequiredQuantities = new HashMap<>();


        for (Map<String, Integer> detailOrderExportRequest : params) {
            int productId = detailOrderExportRequest.get("productId"); // ID sản phẩm
            int requiredQuantity = detailOrderExportRequest.get("quantity"); // Số lượng yêu cầu

            productRequiredQuantities.put(productId,
                    productRequiredQuantities.getOrDefault(productId, 0) + requiredQuantity);
        }

        for (Map.Entry<Integer, Integer> entry : productRequiredQuantities.entrySet()) {
            int productId = entry.getKey();
            int requiredQuantity = entry.getValue();

            List<Productstock> productstocks = this.productStockRepository.findByProductId(
                    this.productRepository.findById(productId));

            int totalAvailable = 0;
            Set<Integer> currentProductWarehouses = new HashSet<>();
            for (Productstock productstock : productstocks) {
                Inventory inventory = productstock.getInventoryId();
                Warehouse warehouse = inventory.getWarehouse();

                if (warehouse != null) {
                    totalAvailable += productstock.getAmount();
                    currentProductWarehouses.add(warehouse.getId());
                    if (totalAvailable >= requiredQuantity) {
                        warehouseIds.add(warehouse.getId());
                        break;
                    }
                }
            }
            warehouseIds.addAll(currentProductWarehouses);
        }

        return warehouseIds.size();
    }

    @Override
    public List<ProductStockRequest> checkProductStock(OrderExportRequest orderExportRequest) {
        List<ProductStockRequest> productStockRequests = new ArrayList<>();
        for (DetailOrderExportRequest detailOrderExportRequest : orderExportRequest.getDetails()){
            int quantityNeed = detailOrderExportRequest.getAmount();
            int productId = detailOrderExportRequest.getProductId();
            List<Productstock> productstockList = this.productStockRepository.findByIdOrderByInventoryEntryDateAsc(productId);
            int currentQuantity = 0;
            for (Productstock productstock : productstockList){
                currentQuantity += productstock.getAmount();
            }
            if(currentQuantity < quantityNeed){
                ProductStockRequest productStockRequest = new ProductStockRequest();
                productStockRequest.setProductId(productId);
                productStockRequest.setProductName(detailOrderExportRequest.getProductName());
                productStockRequest.setAmount(quantityNeed - currentQuantity);
                productStockRequests.add(productStockRequest);
            }
        }
        if (productStockRequests.isEmpty())
            return null;
        else
            return productStockRequests;
    }

    @Override
    public void updateQuantity(OrderExportRequest orderExportRequest) {
        for (DetailOrderExportRequest detailOrderExportRequest : orderExportRequest.getDetails()){
            int quantityNeed = detailOrderExportRequest.getAmount();
            int productId = detailOrderExportRequest.getProductId();
            List<Productstock> productstocks = this.productStockRepository.findByIdOrderByInventoryEntryDateAsc(productId);
            for (Productstock productstock : productstocks){
                int currentQuantity = productstock.getAmount();
                if(currentQuantity >= quantityNeed){
                    int so = currentQuantity - quantityNeed;
                    if(so == 0){
                        this.delete(productstock);
                    }else {
                        productstock.setAmount(so);
                        this.save(productstock);
                    }
                    break;
                }else {
                    quantityNeed -= currentQuantity;
                    this.delete(productstock);
                }
            }
        }
    }

    @Override
    @Transactional
    public void delete(Productstock productstock) {
        Inventory inventory = productstock.getInventoryId();
        Long count = this.productStockRepository.countByInventory(inventory);
        if(count == 0){
            this.inventoryRepository.delete(inventory);
        }
        this.productStockRepository.delete(productstock);
        this.productStockRepository.flush();
    }

    @Override
    public List<Productstock> findAll() {
        return productStockRepository.findAll();
    }

}

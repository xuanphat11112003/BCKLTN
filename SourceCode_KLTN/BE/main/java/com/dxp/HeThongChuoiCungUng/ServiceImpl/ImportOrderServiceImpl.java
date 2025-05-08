package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailImportOrderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.Model.Detailimportorder;
import com.dxp.HeThongChuoiCungUng.Model.Importorder;
import com.dxp.HeThongChuoiCungUng.Repository.ImportOrderRepository;
import com.dxp.HeThongChuoiCungUng.Service.ImportOrderService;
import com.dxp.HeThongChuoiCungUng.Specification.ImportOrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportOrderServiceImpl implements ImportOrderService {
    @Autowired
    private ImportOrderRepository impRepo;
    @Override
    public Importorder saveOrder(Importorder imp) {
        return this.impRepo.save(imp);
    }

    @Override
    public List<ImportorderCreatetionRequest> findImportOrder(Map<String, String> params) {
        Specification<Importorder> specification = ImportOrderSpecification.JoinImportOrder(params);
        List<Importorder> importorders =this.impRepo.findAll(specification);
//        if(true)
//            importorders =
//        else
//            importorders = this.impRepo.findAll();
        return importorders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void upDateActive(int id) {
        Importorder importorder = this.impRepo.findById(id);
        if(importorder.getId() != null){
            importorder.setActive(Boolean.TRUE);
            this.impRepo.save(importorder);
        }
        else{
            throw new RuntimeException("Importorder not found with id: " + id);
        }
    }

    @Override
    public ImportorderCreatetionRequest findById(int id) {
        Importorder importorder = this.impRepo.findById(id);
        System.out.println(importorder.getDetailimportorderCollection());
        return convertToDTO(importorder);
    }

    @Override
    public Importorder getById(int id) {
        return this.impRepo.findById(id);
    }

    @Override
    public void Delete(int id) {
        this.impRepo.delete(this.getById(id));
    }

    private ImportorderCreatetionRequest convertToDTO(Importorder importorder) {
        ImportorderCreatetionRequest dto = new ImportorderCreatetionRequest();
        dto.setId(importorder.getId());
        dto.setOrderDate(importorder.getOrderDate());
        dto.setTotalCost(importorder.getTotalCost());
        dto.setPayment(importorder.getPayment());
        dto.setActive(importorder.getActive());
        dto.setActiveEvaluate(importorder.isActiveEvaluate());
        dto.setImportedBy(importorder.getImportedBy().getUsername()); // Assuming importedBy is a User entity

        List<DetailImportOrderCreatetionRequest> detailDTOs = importorder.getDetailimportorderCollection().stream()
                .map(this::convertDetailToDTO)
                .collect(Collectors.toList());

        dto.setDetail(detailDTOs);
        return dto;
    }

    private DetailImportOrderCreatetionRequest convertDetailToDTO(Detailimportorder detail) {
        DetailImportOrderCreatetionRequest dto = new DetailImportOrderCreatetionRequest();
        dto.setId(detail.getId());
        dto.setQuantity(detail.getQuantity());
        dto.setTotalamout(detail.getTotalAmount());
        dto.setMaterialID(detail.getMaterialID().getId());
        dto.setMaterialName(detail.getMaterialID().getName());
        dto.setSupplierName(detail.getMaterialID().getSupplierId().getName());
        dto.setSupplierID(detail.getMaterialID().getSupplierId().getId());
        return dto;
    }

}

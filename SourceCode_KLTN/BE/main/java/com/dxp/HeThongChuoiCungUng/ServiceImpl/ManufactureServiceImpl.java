package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.Model.Manufacture;
import com.dxp.HeThongChuoiCungUng.Repository.ManufactureRepository;
import com.dxp.HeThongChuoiCungUng.Service.ManufactureService;
import com.dxp.HeThongChuoiCungUng.Specification.ManufactureSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManufactureServiceImpl implements ManufactureService {
    @Autowired
    private ManufactureRepository manufactureRepository;
    @Override
    public void save(Manufacture manufacture) {
        this.manufactureRepository.save(manufacture);
    }

    @Override
    public Manufacture findById(int id) {
        return this.manufactureRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        Manufacture manufacture = this.findById(id);
        this.manufactureRepository.delete(manufacture);
    }

    @Override
    public List<Manufacturerequest> getAllManu(Map<String, String> params) {
        Specification<Manufacture> manufactureSpecification = ManufactureSpecification.Search(params);
        List<Manufacture> manu = this.manufactureRepository.findAll(manufactureSpecification);
        List<Manufacturerequest> dto = new ArrayList<>();
        for(Manufacture ma :manu){
            Manufacturerequest manuDTO = new Manufacturerequest();
            manuDTO.setId(ma.getId());
            manuDTO.setMaterialId(ma.getMaterialId().getId());
            manuDTO.setAmount(ma.getAmount());
            manuDTO.setProductId(ma.getProductId().getId());
            manuDTO.setProductName(ma.getProductId().getName());
            manuDTO.setMaterialName(ma.getMaterialId().getName());
            manuDTO.setUnit(ma.getMaterialId().getUnit());
            manuDTO.setWeight(ma.getMaterialId().getWeight());
            manuDTO.setAgencyMaterial(ma.getMaterialId().getSupplierId().getName());
            manuDTO.setPrice(ma.getMaterialId().getPrice());
            dto.add(manuDTO);
        }
        return dto;
    }

    @Override
    public List<Manufacturerequest> getAllManu() {
        List<Manufacture> manu = this.manufactureRepository.findAll();
        List<Manufacturerequest> dto = new ArrayList<>();
        for(Manufacture ma :manu){
            Manufacturerequest manuDTO = new Manufacturerequest();
            manuDTO.setId(ma.getId());
            manuDTO.setMaterialId(ma.getMaterialId().getId());
            manuDTO.setAmount(ma.getAmount());
            manuDTO.setProductId(ma.getProductId().getId());
            manuDTO.setProductName(ma.getProductId().getName());
            manuDTO.setMaterialName(ma.getMaterialId().getName());
            dto.add(manuDTO);
        }
        return dto;
    }

    @Override
    public void Delete(int id) {
        this.manufactureRepository.delete(this.findById(id));
    }
}

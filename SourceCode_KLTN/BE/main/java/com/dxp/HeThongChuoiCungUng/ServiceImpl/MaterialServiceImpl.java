package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Material;
import com.dxp.HeThongChuoiCungUng.Model.Supplier;
import com.dxp.HeThongChuoiCungUng.Repository.MaterialRepository;
import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
import com.dxp.HeThongChuoiCungUng.Specification.MaterialSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository maRepo;
    @Override
    public Material findById(int id) {
        return this.maRepo.findById(id);
    }

    @Override
    public void save(Material ma) {
        this.maRepo.save(ma);
    }

    @Override
    public List<Material> getALlMaterial(Map<String,String> params) {
        Specification<Material> spec = MaterialSpecification.Search(params);
        return this.maRepo.findAll(spec);
    }

    @Override
    public List<Material> findBySupplierId(Supplier sup) {
        return this.maRepo.findBySupplierId(sup);
    }

    @Override
    public void Delete(int id) {
        this.maRepo.delete(this.findById(id));
    }
}

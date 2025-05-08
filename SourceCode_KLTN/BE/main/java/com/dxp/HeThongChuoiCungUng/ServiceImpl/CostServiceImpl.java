package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Cost;
import com.dxp.HeThongChuoiCungUng.Repository.CostRepository;
import com.dxp.HeThongChuoiCungUng.Service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostServiceImpl implements CostService {
    @Autowired
    private CostRepository costRepository;
    @Override
    public void save(Cost cost) {
        this.costRepository.save(cost);
    }

    @Override
    public List<Cost> findByAll() {
        return this.costRepository.findAll();
    }

    @Override
    public List<Cost> findByType(String type) {
        return this.costRepository.findByType(Cost.Type.fromValue(type));
    }

    @Override
    public Cost findById(int id) {
        return this.costRepository.findById(id);
    }

    @Override
    public void Delete(int id) {
        this.costRepository.delete(this.findById(id));
    }
}

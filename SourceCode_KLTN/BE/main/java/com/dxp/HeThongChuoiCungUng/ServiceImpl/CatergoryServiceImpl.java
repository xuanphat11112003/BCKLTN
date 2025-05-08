package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import com.dxp.HeThongChuoiCungUng.Repository.CatergoryReposity;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import com.dxp.HeThongChuoiCungUng.Specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CatergoryServiceImpl implements CatergoryService {
    @Autowired
    private CatergoryReposity caRepo;
    @Override
    public List<Category> findByType(String type) {
        Category.CategoryType categoryType = Category.CategoryType.fromvalue(type);
        return this.caRepo.findByType(categoryType);
    }

    @Override
    public void save(Category cat) {
        this.caRepo.save(cat);
    }

    @Override
    public List<Category> getAllCategory(Map<String,String> params) {
        Specification<Category> specification = CategorySpecification.Search(params);
        return this.caRepo.findAll(specification);
    }

    @Override
    public Category findById(int id) {
        return this.caRepo.findById(id);
    }

    @Override
    public void Delete(int id) {
        this.caRepo.delete(this.findById(id));
    }

}

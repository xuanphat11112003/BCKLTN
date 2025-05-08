package com.dxp.HeThongChuoiCungUng.Repository;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatergoryReposity extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
    List<Category> findByType(Category.CategoryType type);
    Category findById(int id);
}

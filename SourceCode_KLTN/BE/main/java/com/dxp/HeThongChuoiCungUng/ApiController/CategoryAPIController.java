package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.CategoryRequest;
import com.dxp.HeThongChuoiCungUng.Model.Category;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryAPIController {
    @Autowired
    private CatergoryService catergoryService;
    @GetMapping("api/category")
    @CrossOrigin
    public ResponseEntity<?> getCategory(){
        List<Category> categories = catergoryService.findByType("product");
        List<CategoryRequest> categoryRequests = new ArrayList<>();
        for(Category category : categories){
            categoryRequests.add(new CategoryRequest(category.getId(),category.getName()));
        }
        return ResponseEntity.ok(categoryRequests);
    }

    @DeleteMapping("category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable( value = "id") int id){
        this.catergoryService.Delete(id);
    }
}

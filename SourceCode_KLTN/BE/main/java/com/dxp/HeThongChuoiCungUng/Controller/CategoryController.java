package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class CategoryController {
    @Autowired
    private CatergoryService catService;
    @GetMapping("/categories")
    public String listCategories(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("categories", this.catService.getAllCategory(params));
        return "category/index";
    }
    @GetMapping("/add-category")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add-categorys";
    }
    @PostMapping("/add-category")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "category/add-categorys";
        }
        catService.save(category);
        return "redirect:/categories";
    }
}

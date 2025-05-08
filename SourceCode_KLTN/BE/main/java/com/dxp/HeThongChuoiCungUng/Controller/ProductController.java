package com.dxp.HeThongChuoiCungUng.Controller;

import com.dxp.HeThongChuoiCungUng.Model.Category;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import com.dxp.HeThongChuoiCungUng.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService product;
    @Autowired
    private CatergoryService catergory;
    @RequestMapping("/SeeProduct")
    public String indexProduct(Model model, @RequestParam Map<String,String> params){
        List<Product> p = this.product.getAllProduct(params);
        model.addAttribute("products", p);
        model.addAttribute("categories",catergory.findByType("product"));
        return "product/index";
    }
    @RequestMapping ("/AddProduct")
    public String ShowAddProduct(Model model){
        List<Category> categories = this.catergory.findByType("product");
        model.addAttribute("product", new Product());
        model.addAttribute("categories",categories);
        return "product/add-product";
    }
    @GetMapping("/AddProduct/{id}")
    public String fixProduct(Model model, @PathVariable(value = "id")int id){
        model.addAttribute("product", this.product.findById(id));
        List<Category> categories = this.catergory.findByType("product");
        model.addAttribute("categories",categories);
        return "product/add-product";
    }
    @PostMapping ("/AddProduct")
    public String addProduct(Model model,@Valid @ModelAttribute Product product, BindingResult result) {
        List<Category> categories = this.catergory.findByType("product");
        model.addAttribute("categories",categories);

        if (result.hasErrors()) {
            return "product/add-product";
        }
        try{
            this.product.SaveProduct(product);
        }catch (Exception ex){
            model.addAttribute("errMsg", ex.getMessage());
            return "product/add-product";
        }
        return "redirect:/SeeProduct";
    }
}

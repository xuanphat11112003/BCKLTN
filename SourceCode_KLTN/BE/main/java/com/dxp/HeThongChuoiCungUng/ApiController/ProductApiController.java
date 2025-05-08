package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductApiController {
    @Autowired
    private ProductService productService;

    @GetMapping("/api/products")
    @CrossOrigin
    public ResponseEntity<?> getProduct(@RequestParam Map<String, String> params){
        List<Product> products = this.productService.findAll(params);

        List<ProductRequest> productRequests = new ArrayList<>();

        for(Product p : products){
            ProductRequest productRequest = new ProductRequest();
            productRequest.setId(p.getId());
            productRequest.setName(p.getName());
            productRequest.setImage(p.getImg());
            productRequest.setPrice(p.getPrice());
            productRequests.add(productRequest);
        }
        return ResponseEntity.ok(productRequests);
    }
    @DeleteMapping("product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable (value = "id") int id){
        this.productService.Delete(id);
    }
}

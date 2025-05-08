package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ProductRequest;
import com.dxp.HeThongChuoiCungUng.Model.Product;
import com.dxp.HeThongChuoiCungUng.Repository.ProductRepository;
import com.dxp.HeThongChuoiCungUng.Service.ProductService;
import com.dxp.HeThongChuoiCungUng.Specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private Cloudinary cloudinary;
    @Override

    public List<Product> getAllProduct(Map<String,String> params) {
        Specification<Product> specification =ProductSpecification.Search(params);
        return this.productRepo.findAll(specification);
    }

    @Override
    public Product findById(int id) {
        return this.productRepo.findById(id);
    }

    @Override
    @CachePut(value = "products", key = "#p.toString()")
    public Product SaveProduct(Product p) {
        if(!p.getFile().isEmpty()){
            try{
                Map res =  this.cloudinary.uploader().upload(p.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                p.setImg(res.get("secure_url").toString());
            }catch (java.io.IOException ex){
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return productRepo.save(p);
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepo.findAll();
    }

    @Override
    public List<ProductRequest> getProductResponse(Map<String, String> params) {
        List<Product> products = this.getAllProduct(params);

        List<ProductRequest> productRequests = new ArrayList<>();

        for(Product p : products){
            ProductRequest productRequest = new ProductRequest();
            productRequest.setId(p.getId());
            productRequest.setName(p.getName());
            productRequest.setImage(p.getImg());
            productRequest.setPrice(p.getPrice());
            productRequests.add(productRequest);
        }
        return productRequests;
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void Delete(int id) {
        this.productRepo.delete(this.findById(id));
    }

    @Override
    @Cacheable(value = "products", key = "#params.toString()")
    public List<Product> findAll(Map<String, String> params) {
        String pageValue = params.get("page");
        int page = (pageValue != null && !pageValue.isEmpty()) ? Integer.parseInt(pageValue) : 0;
        int size = 6;
        Pageable pageable = PageRequest.of(page, size);
        Specification<Product> specification = ProductSpecification.Search(params);
        Page<Product> products = this.productRepo.findAll(specification, pageable);
        return products.getContent();
    }
}

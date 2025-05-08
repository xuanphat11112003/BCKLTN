package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.Manufacturerequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.MaterialStockRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderDetailManufactureRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.OrderManufactureRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
public class ManufactureApiController {
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private OrderManufactureService orderManufactureService;
    @Autowired
    private OrderDetailManufactureService orderDetailManufactureService;
    @Autowired
    private MaterialStockService materialStockService;

    @PostMapping("/manufacture")
    public ResponseEntity<?> AddManufacture(@RequestBody List<Manufacturerequest> manufacturerequest){

        try{
            if(!manufacturerequest.isEmpty()){
                for(Manufacturerequest manuDTO : manufacturerequest){
                    Manufacture manufacture = new Manufacture();
                    manufacture.setAmount(manuDTO.getAmount());
                    manufacture.setProductId(this.productService.findById(manuDTO.getProductId()));
                    manufacture.setMaterialId(this.materialService.findById(manuDTO.getMaterialId()));
                    this.manufactureService.save(manufacture);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @PostMapping("manufacture/order")
    public ResponseEntity<?> createOrderManufacture(@RequestBody OrderManufactureRequest orderManufactureRequest,
                                                    Principal principal){
        List<Manufacturerequest> manufacturerequestsDTO = new ArrayList<>();
        for(OrderDetailManufactureRequest orderDetailManufactureRequest : orderManufactureRequest.getDetails()){
            int quantity = orderDetailManufactureRequest.getQuantity();
            int productId = orderDetailManufactureRequest.getProductId();
            Map<String,String> params = new HashMap<>();
            params.put("productId",String.valueOf(productId));
            List<Manufacturerequest> manufacturerequests = this.manufactureService.getAllManu(params);
            if(manufacturerequests.isEmpty() || manufacturerequests == null){
                return new ResponseEntity<>("sản phẩm chưa có công thức",HttpStatus.BAD_REQUEST);
            }
            for(Manufacturerequest manu : manufacturerequests){
                Manufacturerequest manuDTO = new Manufacturerequest();
                manuDTO.setProductId(manu.getProductId());
                manuDTO.setAmount(manu.getAmount()*quantity);
                manuDTO.setMaterialId(manu.getMaterialId());
                manuDTO.setWeight(manu.getWeight());
                manufacturerequestsDTO.add(manuDTO);
            }
        }
        if(manufacturerequestsDTO.isEmpty()){
            return new ResponseEntity<>("sản phẩm chưa có công thức",HttpStatus.BAD_REQUEST);
        }
        Map<Integer, MaterialStockRequest> checklist = this.inventoryService.checkMaterialAvailable(manufacturerequestsDTO);
        if(checklist == null || checklist.isEmpty()){
            OrderManufacture orderManufacture = new OrderManufacture();
            User user = this.userService.LoadUserByName(principal.getName());
            orderManufacture.setOrder_by(user);
            Date date = new Date();
            orderManufacture.setCreateDate(date);
            orderManufacture.setActive(true);
            orderManufacture.setTransaction("Đã Nhận");
            orderManufacture.setWarehouseID(this.wareHouseService.findById(orderManufactureRequest.getWareHouseId()));
            this.orderManufactureService.save(orderManufacture);
            for(OrderDetailManufactureRequest orderDetailManufactureRequest : orderManufactureRequest.getDetails()){
                OrderDetailManufacture orderDetailManufacture = new OrderDetailManufacture();
                orderDetailManufacture.setOrderManufacture(orderManufacture);
                orderDetailManufacture.setQuantity(orderDetailManufactureRequest.getQuantity());
                orderDetailManufacture.setProduct(this.productService.findById(orderDetailManufactureRequest.getProductId()));
                this.orderDetailManufactureService.save(orderDetailManufacture);
                this.materialStockService.updateQuantity(manufacturerequestsDTO);
            }
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(checklist,HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/manufacture/orderPending")
    public ResponseEntity<?> addPendingOrderManufacture(@RequestBody OrderManufactureRequest orderManufactureRequest,
                                                        Principal principal){
        List<Manufacturerequest> manufacturerequestsDTO = new ArrayList<>();
        for(OrderDetailManufactureRequest orderDetailManufactureRequest : orderManufactureRequest.getDetails()){
            int quantity = orderDetailManufactureRequest.getQuantity();
            int productId = orderDetailManufactureRequest.getProductId();
            Map<String,String> params = new HashMap<>();
            params.put("productId",String.valueOf(productId));
            List<Manufacturerequest> manufacturerequests = this.manufactureService.getAllManu(params);
            for(Manufacturerequest manu : manufacturerequests){
                Manufacturerequest manuDTO = new Manufacturerequest();
                manuDTO.setProductId(manu.getProductId());
                manuDTO.setAmount(manu.getAmount()*quantity);
                manuDTO.setMaterialId(manu.getMaterialId());
                manufacturerequestsDTO.add(manuDTO);
            }
        }
        try{
            OrderManufacture orderManufacture = new OrderManufacture();
            User user = this.userService.LoadUserByName(principal.getName());
            Date date = new Date();
            orderManufacture.setOrder_by(user);
            orderManufacture.setCreateDate(date);
            orderManufacture.setActive(false);
            orderManufacture.setTransaction("Đang đợi hàng về");
            orderManufacture.setWarehouseID(this.wareHouseService.findById(orderManufactureRequest.getWareHouseId()));
            this.orderManufactureService.save(orderManufacture);
            for(OrderDetailManufactureRequest manufactureRequest : orderManufactureRequest.getDetails()){
                OrderDetailManufacture orderDetailManufacture = new OrderDetailManufacture();
                orderDetailManufacture.setProduct(this.productService.findById(manufactureRequest.getProductId()));
                orderDetailManufacture.setQuantity(manufactureRequest.getQuantity());
                orderDetailManufacture.setOrderManufacture(orderManufacture);
                this.orderDetailManufactureService.save(orderDetailManufacture);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.materialStockService.updataActice(manufacturerequestsDTO);

        return new ResponseEntity<>("ok",HttpStatus.OK);
    }
    @PostMapping("/orderManufacture/{orderManufactureId}")
    public ResponseEntity<?> acceptOrderManufacture(@PathVariable (value = "orderManufactureId") int id){
        OrderManufactureRequest orderManufactureRequest = this.orderManufactureService.findById(id);
        List<Manufacturerequest> manufacturerequestsDTO = new ArrayList<>();
        for(OrderDetailManufactureRequest orderDetailManufactureRequest : orderManufactureRequest.getDetails()){
            int quantity = orderDetailManufactureRequest.getQuantity();
            int productId = orderDetailManufactureRequest.getProductId();
            Map<String,String> params = new HashMap<>();
            params.put("productId",String.valueOf(productId));
            List<Manufacturerequest> manufacturerequests = this.manufactureService.getAllManu(params);
            if(manufacturerequests.isEmpty() || manufacturerequests == null){
                return new ResponseEntity<>("sản phẩm chưa có công thức",HttpStatus.BAD_REQUEST);
            }
            for(Manufacturerequest manu : manufacturerequests){
                Manufacturerequest manuDTO = new Manufacturerequest();
                manuDTO.setProductId(manu.getProductId());
                manuDTO.setAmount(manu.getAmount()*quantity);
                manuDTO.setMaterialId(manu.getMaterialId());
                manufacturerequestsDTO.add(manuDTO);
            }
        }

        Map<Integer, MaterialStockRequest> checklist = this.inventoryService.checkRemainMaterialAvailable(manufacturerequestsDTO);
        if(checklist == null || checklist.isEmpty() ){
            OrderManufacture orderManufacture = this.orderManufactureService.getById(id);
            orderManufacture.setTransaction("Đã Nhận");
            this.orderManufactureService.save(orderManufacture);
            this.materialStockService.updateQuantity(manufacturerequestsDTO);
            return new ResponseEntity<>("ok",HttpStatus.OK);
        }else {
            return new ResponseEntity<>(checklist,HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("orderManufacture/{orderId}")
    public ResponseEntity<?> processOrderManufacture(@PathVariable (value = "orderId") int id){
        this.orderManufactureService.upDateTransaction(id,"Đang thực hiện");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @DeleteMapping("orderManufacture/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderManufacture(@PathVariable (value = "orderId") int id){
        OrderManufactureRequest orderManufactureRequest = this.orderManufactureService.findById(id);
        List<Manufacturerequest> manufacturerequestsDTO = new ArrayList<>();
        for(OrderDetailManufactureRequest orderDetailManufactureRequest : orderManufactureRequest.getDetails()){
            int quantity = orderDetailManufactureRequest.getQuantity();
            int productId = orderDetailManufactureRequest.getProductId();
            Map<String,String> params = new HashMap<>();
            params.put("productId",String.valueOf(productId));
            List<Manufacturerequest> manufacturerequests = this.manufactureService.getAllManu(params);
            for(Manufacturerequest manu : manufacturerequests){
                Manufacturerequest manuDTO = new Manufacturerequest();
                manuDTO.setProductId(manu.getProductId());
                manuDTO.setAmount(manu.getAmount()*quantity);
                manuDTO.setMaterialId(manu.getMaterialId());
                manufacturerequestsDTO.add(manuDTO);
            }
        }
        this.materialStockService.restoreQuantity(manufacturerequestsDTO);
        this.orderManufactureService.delete(id);
    }

}

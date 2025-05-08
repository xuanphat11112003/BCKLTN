package com.dxp.HeThongChuoiCungUng.ApiController;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DataMailRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailImportOrderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Service.*;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ImportOderApiIController {
    @Autowired
    private ImportOrderService importsv;
    @Autowired
    private MaterialService ma;
    @Autowired
    private DetailImportOrderService detailImportOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @PostMapping("/import-order")
    public ResponseEntity<?> createOrder(@RequestBody ImportorderCreatetionRequest imp, Principal principal) {
        try {
            Importorder importorder = new Importorder();
            Date daye = new Date();
            importorder.setOrderDate(daye);
            User u = this.userService.LoadUserByName(principal.getName());
            Importorder.Payment payment = Importorder.Payment.fromValue(imp.getPayment());
            importorder.setPayment(payment.getValue());
            importorder.setImportedBy(u);
            importorder.setTotalCost(imp.getTotalCost());
            importorder.setActive(Boolean.FALSE);
            importorder.setActiveEvaluate(Boolean.FALSE);

            importsv.saveOrder(importorder);
            String mail= "";
            for (DetailImportOrderCreatetionRequest detail : imp.getDetail()) {
                Detailimportorder details = new Detailimportorder();
                details.setQuantity(detail.getQuantity());
                details.setTotalAmount(detail.getTotalamout());
                Material ma = this.ma.findById(detail.getMaterialID());
                details.setMaterialID(ma);
                details.setImportOrderID(importorder);
                mail = details.getMaterialID().getSupplierId().getEmail();
                this.detailImportOrderService.save(details);
            }
            String email = mail;
            // Generate invoice PDF
            String pdfFilePath = "temp/invoice-" + importorder.getId() + ".pdf";
            Map<String, Object> pdfProps = Map.of(
                    "orderId", importorder.getId(),
                    "totalCost", importorder.getTotalCost(),
                    "orderDate", importorder.getOrderDate().toString(),
                    "details", imp.getDetail()
            );
            mailService.generatePdfFromTemplate("mail/pdfFile", pdfProps, pdfFilePath);
            DataMailRequest mailRequest = new DataMailRequest();
            mailRequest.setTo(email);
            mailRequest.setSubject("New Import Order Created");
            mailRequest.setContent("Please find the attached invoice for the import order.");
            Map<String, Object> props = Map.of(
                    "orderId", importorder.getId(),
                    "totalCost", importorder.getTotalCost(),
                    "orderDate", importorder.getOrderDate().toString(),
                    "creatorName", u.getUsername()
            );
            mailRequest.setProps(props);
            mailRequest.setAttachments(List.of(pdfFilePath));

            // Send email
            mailService.sendMailWithTemplate(mailRequest);
//            File pdfFile = new File(pdfFilePath);
//            if (pdfFile.exists()) {
//                pdfFile.delete();
//            }

            return new ResponseEntity<>(imp, HttpStatus.CREATED);

        } catch ( IOException | MessagingException e) {
            // Handle exceptions related to PDF generation or email sending
            return new ResponseEntity<>("An error occurred while creating the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/import-order/suppliers")
    public ResponseEntity<?> createOrders(@RequestBody List<ImportorderCreatetionRequest> params,Principal principal) throws IOException, MessagingException {
        Date date = new Date();
        User user = this.userService.LoadUserByName(principal.getName());
        for(ImportorderCreatetionRequest suppliers : params){
            Importorder importorder = new Importorder();
            importorder.setPayment(suppliers.getPayment());
            importorder.setOrderDate(date);
            importorder.setImportedBy(user);
            importorder.setActive(false);
            importorder.setActiveEvaluate(false);
            this.importsv.saveOrder(importorder);
            List<DetailImportOrderCreatetionRequest> Details =  suppliers.getDetail();
            long totalamout = 0;
            String mail = "";
            for (DetailImportOrderCreatetionRequest materials : Details){
                Detailimportorder detailimportorder = new Detailimportorder();
                detailimportorder.setImportOrderID(importorder);
                Material material = this.ma.findById(materials.getMaterialID());
                detailimportorder.setMaterialID(material);
                long amount = material.getPrice();
                long quantity =  materials.getQuantity();
                detailimportorder.setQuantity(materials.getQuantity());
                detailimportorder.setTotalAmount(amount*quantity);
                totalamout += (amount *quantity);
                importorder.setTotalCost(totalamout);
                mail = detailimportorder.getMaterialID().getSupplierId().getEmail();
                materials.setMaterialName(material.getName());
                materials.setTotalamout(totalamout);
                this.importsv.saveOrder(importorder);
                this.detailImportOrderService.save(detailimportorder);
            }
            String email = mail;
            String pdfFilePath = "temp/invoice-" + importorder.getId() + ".pdf";

            Map<String, Object> pdfProps = Map.of(
                    "orderId", importorder.getId(),
                    "totalCost", importorder.getTotalCost(),
                    "orderDate", importorder.getOrderDate().toString(),
                    "details", suppliers.getDetail()
            );
            mailService.generatePdfFromTemplate("mail/pdfFile", pdfProps, pdfFilePath);
            DataMailRequest mailRequest = new DataMailRequest();
            mailRequest.setTo(email);
            mailRequest.setSubject("New Import Order Created");
            mailRequest.setContent("Please find the attached invoice for the import order.");
            Map<String, Object> props = Map.of(
                    "orderId", importorder.getId(),
                    "totalCost", importorder.getTotalCost(),
                    "orderDate", importorder.getOrderDate().toString(),
                    "creatorName", user.getUsername()
            );
            mailRequest.setProps(props);
            mailRequest.setAttachments(List.of(pdfFilePath));

            // Send email
            mailService.sendMailWithTemplate(mailRequest);
            File pdfFile = new File(pdfFilePath);
            if (pdfFile.exists()) {
                pdfFile.delete();
            }

        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}

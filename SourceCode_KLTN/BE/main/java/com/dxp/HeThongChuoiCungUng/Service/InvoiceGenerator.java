package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DetailImportOrderCreatetionRequest;
import com.dxp.HeThongChuoiCungUng.DTO.Request.ImportorderCreatetionRequest;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface InvoiceGenerator {
    public void generatePdf(String htmlContent, String filePath) throws IOException;
}

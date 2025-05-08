package com.dxp.HeThongChuoiCungUng.Service;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DataMailRequest;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface MailService {
    public void sendMailWithTemplate(DataMailRequest request) throws MessagingException, IOException;
    public void generatePdfFromTemplate(String templateName, Map<String, Object> props, String filePath) throws IOException;
}

package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.DTO.Request.DataMailRequest;
import com.dxp.HeThongChuoiCungUng.Service.MailService;
import com.itextpdf.html2pdf.HtmlConverter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendMailWithTemplate(DataMailRequest request) throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());


        String htmlContent = generateContentFromTemplate(request.getProps(), "mail/invoice");
        helper.setText(htmlContent, true);

        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            for (String filePath : request.getAttachments()) {
                File file = new File(filePath);
                if (file.exists()) {
                    helper.addAttachment(file.getName(), file);
                }
            }
        }

        // Send the email
        mailSender.send(message);
    }

    private String generateContentFromTemplate(Map<String, Object> props, String templateName) {
        Context context = new Context();
        context.setVariables(props);
        return templateEngine.process(templateName, context);
    }

    @Override
    public void generatePdfFromTemplate(String templateName, Map<String, Object> props, String filePath) throws IOException {
        Context context = new Context();
        context.setVariables(props);
        String htmlContent = templateEngine.process(templateName, context);
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
        }
    }
}

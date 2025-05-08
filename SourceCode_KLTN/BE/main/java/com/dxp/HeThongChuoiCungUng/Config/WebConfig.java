package com.dxp.HeThongChuoiCungUng.Config;


import com.dxp.HeThongChuoiCungUng.Formatter.CategoryFormatter;
import com.dxp.HeThongChuoiCungUng.Service.CatergoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryFormatter());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

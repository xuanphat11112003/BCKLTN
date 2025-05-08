package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class DataMailRequest {
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;
    private List<String> attachments;
}

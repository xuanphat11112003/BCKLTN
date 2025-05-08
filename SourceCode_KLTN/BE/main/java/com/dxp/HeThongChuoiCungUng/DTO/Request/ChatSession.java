package com.dxp.HeThongChuoiCungUng.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatSession {
    private String sessionId;
    private String customerId;
    private String employeeId;
}

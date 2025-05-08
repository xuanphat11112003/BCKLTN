package com.dxp.HeThongChuoiCungUng.ApiController;
import com.dxp.HeThongChuoiCungUng.Service.AgencyService;
import com.dxp.HeThongChuoiCungUng.Service.PartnerService;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PartnerApiController {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private UserService userService;
    @Autowired
    private PartnerService partnerService;

}

package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dxp.HeThongChuoiCungUng.DTO.Request.PartnerRequest;
import com.dxp.HeThongChuoiCungUng.Model.*;
import com.dxp.HeThongChuoiCungUng.Repository.*;
import com.dxp.HeThongChuoiCungUng.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private PartnerAgencyRepository partnerAgencyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private PartnerTransportRepository partnerTransportRepository;
    @Autowired
    private TransportPartnerRepository transportPartnerRepository;

    @Override
    public void save(Partner partner) {
        Partner partner1 = new Partner();
        partner1.setType(partner.getType());
        partner1.setEndDate(partner.getEndDate());
        partner1.setName(partner.getName());
        partner1.setCreateDate(partner.getCreateDate());
        partner1.setActive(partner.getActive());
        partner1.setContactInfo(partner.getContactInfo());
        this.partnerRepository.save(partner1);
        if(partner.getType() == Partner.Type.AGENCY){

            User user = new User();
            if (!partner.getPartnerAgency().getAgency().getUser().getFile().isEmpty()){
                try {
                    Map res =  this.cloudinary.uploader().upload(partner.getPartnerAgency().getAgency().getUser().getFile().getBytes(),
                            ObjectUtils.asMap("resource_type","auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            user.setUserRole(User.UserRole.ROLE_AGENCY);
            user.setFirstname(partner.getPartnerAgency().getAgency().getUser().getFirstname());
            user.setLastname(partner.getPartnerAgency().getAgency().getUser().getLastname());
            user.setAddress(partner.getPartnerAgency().getAgency().getUser().getAddress());
            user.setUsername(partner.getPartnerAgency().getAgency().getUser().getUsername());
            user.setPassword(passwordEncoder.encode(partner.getPartnerAgency().getAgency().getUser().getPassword()));
            user.setActive(false);
            user.setEmail(partner.getPartnerAgency().getAgency().getUser().getEmail());
            Date date = new Date();
            user.setCreatedDate(date);
            this.userRepository.save(user);
            Agency agency = new Agency();
            agency.setManagerName(partner.getPartnerAgency().getAgency().getManagerName());
            agency.setUser(user);
            this.agencyRepository.save(agency);
            partnerAgency partnerA = new partnerAgency();
            partnerA.setPartner(partner1);
            partnerA.setAgency(agency);
            this.partnerAgencyRepository.save(partnerA);
        }else {
            Transportpartner transportpartner = new Transportpartner();
            transportpartner.setAddress(partner.getPartnerTransport().getTransportpartner().getAddress());
            transportpartner.setEmail(partner.getPartnerTransport().getTransportpartner().getEmail());
            transportpartner.setName(partner.getPartnerTransport().getTransportpartner().getName());
            transportpartner.setNumberPhone(partner.getPartnerTransport().getTransportpartner().getNumberPhone());
            this.transportPartnerRepository.save(transportpartner);
            partnerTransport transport = new partnerTransport();
            transport.setPartner_trans(partner1);
            transport.setTransportpartner(transportpartner);
            this.partnerTransportRepository.save(transport);
        }
    }

    @Override
    public List<PartnerRequest> getAll(Map<String, String> params) {
        List<Partner> partners = new ArrayList<>();
        if(params.isEmpty())
            partners = this.partnerRepository.findAll();
        else
            partners = this.partnerRepository.findByName(params.get("name"));
        return partners.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    @Override
    public Partner findById(int id) {
        return this.partnerRepository.findById(id);
    }

    @Override
    public void addContract(Partner partner, int id) {
        if(Partner.Type.AGENCY.equals(partner.getType())){
            Agency agency = this.agencyRepository.findById(id);
            partner.setName(agency.getManagerName());
            this.partnerRepository.save(partner);
            partnerAgency partnerA = new partnerAgency();
            partnerA.setPartner(partner);
            partnerA.setAgency(agency);
            this.partnerAgencyRepository.save(partnerA);
        }else {
            partnerTransport partnerT = new partnerTransport();
            Transportpartner transportpartner = this.transportPartnerRepository.findById(id);
            partner.setName(transportpartner.getName());
            this.partnerRepository.save(partner);
            partnerT.setPartner_trans(partner);
            partnerT.setTransportpartner(transportpartner);
            this.partnerTransportRepository.save(partnerT);
        }
    }

    @Override
    public void Delete(int id) {
        this.partnerRepository.delete(this.findById(id));
    }

    private PartnerRequest convertDTO(Partner partner){
        PartnerRequest partnerRequest = new PartnerRequest();
        partnerRequest.setId(partner.getId());
        partnerRequest.setContractInfo(partner.getContactInfo());
        partnerRequest.setName(partner.getName());
        partnerRequest.setEntryDate(partner.getEndDate());
        partnerRequest.setCreateDate(partner.getCreateDate());
        if(!partner.getEndDate().after(new Date())){
            partner.setActive(false);
            this.partnerRepository.save(partner);
        }
        partnerRequest.setActive(partner.getActive());
        if(Partner.Type.AGENCY.equals(partner.getType())){
            partnerRequest.setAgencyId(partner.getPartnerAgency().getAgency().getId());
            partnerRequest.setAgencyName(partner.getPartnerAgency().getAgency().getManagerName());
        }else {
            partnerRequest.setTransPortId(partner.getPartnerTransport().getTransportpartner().getId());
            partnerRequest.setTransPortName(partner.getPartnerTransport().getTransportpartner().getName());
        }
        return partnerRequest;
    }
}

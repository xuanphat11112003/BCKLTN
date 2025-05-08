package com.dxp.HeThongChuoiCungUng.ServiceImpl;

import com.dxp.HeThongChuoiCungUng.Model.Agency;
import com.dxp.HeThongChuoiCungUng.Model.User;
import com.dxp.HeThongChuoiCungUng.Repository.AgencyRepository;
import com.dxp.HeThongChuoiCungUng.Service.AgencyService;
import com.dxp.HeThongChuoiCungUng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private UserService userService;

    @Override
    public void save(Agency agency) {
        this.agencyRepository.save(agency);
    }

    @Override
    public Agency findById(int id) {
        return this.agencyRepository.findById(id);
    }

    @Override
    public List<Agency> findAll() {
        return this.agencyRepository.findAll();
    }

    @Override
    public void Delete(int id) {
        this.agencyRepository.delete(this.findById(id));
    }

    @Override
    public Agency findByName(String name) {
        User user = this.userService.LoadUserByName(name);
        Agency agency = this.agencyRepository.findByUserAID_Id(user.getId());
        return agency;
    }

//    @Override
//    public List<Agency> findActiveTrue() {
//        return this.agencyRepository.findActiveAgencies();
//    }
}

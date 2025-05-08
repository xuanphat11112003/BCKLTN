    package com.dxp.HeThongChuoiCungUng.DTO.Request;


    import com.dxp.HeThongChuoiCungUng.Model.Material;
    import com.dxp.HeThongChuoiCungUng.Service.MaterialService;
    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.beans.factory.annotation.Autowired;

    @Getter
    @Setter
    public class DetailImportOrderCreatetionRequest {
        private int id;
        private int quantity;
        private Long totalamout;
        private int materialID;
        private String materialName;
        private String supplierName;
        private int supplierID;

        public DetailImportOrderCreatetionRequest() {
        }
        public DetailImportOrderCreatetionRequest(String Name,int quantity) {
            this.materialName = Name;
            this.quantity = quantity;
        }

    }

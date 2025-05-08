package com.dxp.HeThongChuoiCungUng.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "Partner_Agency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class partnerAgency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @JoinColumn(name = "PartnerId", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Agency agency;
    @OneToOne
    @JoinColumn(name = "partnerIds")
    private Partner partner;
}

package com.dxp.HeThongChuoiCungUng.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "partnerTransport")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class partnerTransport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @JoinColumn(name = "Partner_trans", referencedColumnName = "id")
    @ManyToOne
    private Transportpartner transportpartner;
    @OneToOne
    @JoinColumn(name = "partner_Id")
    private Partner partner_trans;
}

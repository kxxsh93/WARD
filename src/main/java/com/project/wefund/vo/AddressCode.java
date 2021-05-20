package com.project.wefund.vo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class AddressCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String totalCode;
    public String address;
    public String state;
    public String sigunguCd;
    public String bjdongCd;
    public Boolean codeSavePoint;
}

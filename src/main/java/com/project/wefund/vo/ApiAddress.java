package com.project.wefund.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class ApiAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties(ignoreUnknown = true) //json을 vo 매핑하는 도중 property가 null일 경우 에러나는것을 방지
    private Integer id;

    public String bjdongCd;
    public String platPlc;
    public String bldNm;
    public String naRoadCd;
    public String oudrMechUtcnt;
    public String splotNm;
    public String ugrndFlrCnt;
    public String pmsnoYear;
    public String roofCd;
    public String hoCnt;
    public Integer naSubBun;
    public String mgmBldrgstPk;
    public String oudrAutoUtcnt;
    public String crtnDay;
    public String totDongTotArea;
    public String atchBldCnt;
    public String dongNm;
    public String stcnsDay;
    public String block;
    public String indrMechArea;
    public String archArea;
    public String pmsnoGbCd;
    public String regstrGbCdNm;
    public String gnBldCert;
    public String vlRatEstmTotArea;
    public String atchBldArea;
    public String etcPurps;
    public String mainAtchGbCdNm;
    public String oudrMechArea;
    public String rideUseElvtCnt;
    public String strctCd;
    public String indrAutoArea;
    public String roofCdNm;
    public Integer naMainBun;
    public String bcRat;
    public String regstrKindCd;
    public String totArea;
    public String oudrAutoArea;
    public String sigunguCd;
    public String strctCdNm;
    public String vlRat;
    public String ji;
    public String engrEpi;
    public String gnBldGrade;
    public String mainPurpsCd;
    public String platGbCd;
    public String rserthqkDsgnApplyYn;
    public String naUgrndCd;
    public Integer bylotCnt;
    public String regstrGbCd;
    public String grndFlrCnt;
    public String etcStrct;
    public String indrAutoUtcnt;
    public String fmlyCnt;
    public String lot;
    public String emgenUseElvtCnt;
    public String pmsnoKikCdNm;
    public String indrMechUtcnt;
    public String itgBldGrade;
    public String heit;
    public String regstrKindCdNm;
    public String engrRat;
    public String rserthqkAblty;
    public String itgBldCert;
    public String pmsnoGbCdNm;
    public String rnum;
    public String pmsnoKikCd;
    public String etcRoof;
    public String engrGrade;
    public String mainPurpsCdNm;
    public String useAprDay;
    public String platArea;
    public String hhldCnt;
    public String mainAtchGbCd;
    public String pmsDay;
    public String naBjdongCd;
    public String bun;
    public String newPlatPlc;

//    public Boolean addressSavePoint;
    // Null 값, 나중에 Data 추가 시 api data 갱신할 때 같이 리셋되지 않게 하려는 목적

}

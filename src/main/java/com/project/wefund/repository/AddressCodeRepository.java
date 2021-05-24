package com.project.wefund.repository;

import com.project.wefund.vo.AddressCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AddressCodeRepository extends JpaRepository<AddressCode, String> {
//    @Query("select a.totalCode from AddressCode a")
//    List<String> getTotalCode();

    List<AddressCode> findAllByCodeSavePoint(Boolean codeSavePoint);


    // 해당하는 데이터 저장 후, codeSavePoint = true로 변경
    @Transactional
    @Modifying
    @Query("update AddressCode c set c.codeSavePoint = true where c.sigunguCd = :sigunguCd and c.bjdongCd = :bjdongCd")
    void updateCodeSavePoint(String sigunguCd, String bjdongCd);


    // 임시 생성, codeSavePoint 전부 변경 메소드
    @Transactional
    @Modifying
    @Query("update AddressCode set codeSavePoint = null")
    void sdsd();

}

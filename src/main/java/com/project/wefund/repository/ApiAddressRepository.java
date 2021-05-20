package com.project.wefund.repository;

import com.project.wefund.vo.AddressCode;
import com.project.wefund.vo.ApiAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApiAddressRepository extends JpaRepository<ApiAddress, Long> {

//    @Query("select a.totalCode from AddressCode a")
    List<Object> findBySigunguCdAndBjdongCd(String sigunguCd, String bjdongCd);

    @Transactional
    @Modifying
    @Query("delete from ApiAddress c where c.sigunguCd = :sigunguCd and c.bjdongCd = :bjdongCd")
    void deleteData(String sigunguCd, String bjdongCd);

    boolean existsByBjdongCd(String bjdongCd);

//    @Query("select exists (select 1 from ApiAddress where sigunguCd = :sigunguCd and bjdongCd = :bjdongCd)")
//    void searchData(String sigunguCd, String bjdongCd);

//    @Query("select count(c.id) > 0 from ApiAddress c where c.sigunguCd = :sigunguCd and c.bjdongCd = :bjdongCd")
//    void exists(String sigunguCd, String bjdongCd);

}

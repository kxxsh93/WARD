package com.project.wefund.controller;

import com.project.wefund.repository.AddressCodeRepository;
import com.project.wefund.util.URLCode;
import com.project.wefund.vo.AddressCode;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Main Controller -> 건축물대장 표제부 조회 후 전체 데이터 저장하는 Class
@RestController
@RequestMapping(path = "/apitest", method = {RequestMethod.POST})
public class SaveAllApiAddressController_v4 {
    @Autowired
    private AddressCodeRepository addressCodeRepository;    // AddressCodeRepository 주입

    // *******
    // URLCode 주입안하면 Repository 오류
    // *******
    @Autowired
    private URLCode urlCode;

    @SneakyThrows
    @PostMapping
    public String SaveAllData() {   // 저장하는 메인 Method
        List<AddressCode> addressCodeList = addressCodeRepository.findAllByCodeSavePoint(false); // codeSavePoint가 전부 null값인 addressCode값 리스트
        for (AddressCode addressCode : addressCodeList) {
            String testcode = addressCode.getTotalCode();   // testcode 추출 -> testcode = sigunguCd + bjdongCd
//            System.out.println(testcode);
            Long numOfRows = urlCode.checkTotalCount(testcode); // 처음에 api한번 조회. Why? => totalCnt를 조회하기 위해서 조회.
            // 1. 해당 testcode에 api 호출
            // 2. 해당 Data에 totalCnt(해당 지역(sigunguCd, bjdongCd) 건축물 정보의 개수)가 나옴
            // 3. Api 호출 Url에 추출한 totalCnt값을 넣어 한번에 모든 Data를 호출할 수 있음
            String url = urlCode.makeURL(urlCode.defaultURLDataForSaveAllData(testcode, numOfRows));    // 해당 지역 전체 Data를 조회할 수 있는 url

            try {
                JSONObject pureData = urlCode.preprocessingData(url);   // 호출한 raw Data 필요한 값만 파싱하여 pureData로 저장
                urlCode.printAddressData(pureData, testcode.substring(0, 5), testcode.substring(5));    // pureData DB에 저장하는 Method 실행

            } catch (ClassCastException c) {
                // 값이 없는 Data(Data 조회안되는)일 경우 예외 처리
                System.out.println("값이 없음.");
                Thread.sleep(3000);
                // Ex. 경기도 화성시 장안면 => 상위 Data라 조회값 없음
            }
        }
        return "전국 건축물대장 표제부 조회 Data 저장";
    }
}

package com.project.wefund.controller;

import com.project.wefund.repository.AddressCodeRepository;
import com.project.wefund.repository.ApiAddressRepository;
import com.project.wefund.util.URLCode;
import com.project.wefund.vo.AddressCode;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/apitest", method = {RequestMethod.POST})
public class SaveAllApiAddressController_v4 {
//    @Autowired
//    private ApiAddressRepository apiAddressRepository;
    @Autowired
    private AddressCodeRepository addressCodeRepository;

    // *******매우 중요..
    @Autowired
    private URLCode urlCode;

    @SneakyThrows
    @PostMapping
    public String SaveAllData() {
        List<AddressCode> addressCodeList = addressCodeRepository.findAllByCodeSavePoint(false); // codeSavePoint가 전부 null값인
        for (AddressCode addressCode : addressCodeList) {
            String testcode = addressCode.getTotalCode();

            System.out.println(testcode);
            Long numOfRows = urlCode.checkTotalCount(testcode);
            String url = urlCode.makeURL(urlCode.defaultURLDataForSaveAllData(testcode, numOfRows));

            try {
                JSONObject pureData = urlCode.preprocessingData(url);
                urlCode.printAddressData(pureData, testcode.substring(0, 5), testcode.substring(5));

            } catch (ClassCastException c) {
                System.out.println("값이 없음.");
                Thread.sleep(3000);
                // Ex. 경기도 화성시 장안면 => 상위 Data라 조회값 없음
            }
        }
        return "전국 건축물대장 표제부 조회 Data 저장";
    }
}

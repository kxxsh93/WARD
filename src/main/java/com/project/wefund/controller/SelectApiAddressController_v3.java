package com.project.wefund.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.wefund.repository.ApiAddressRepository;
import com.project.wefund.util.URLCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "findApiAddress", method = {RequestMethod.POST})
public class SelectApiAddressController_v3 {
    //
    @Autowired
    private URLCode urlCode;
    // URLCode 주입
    @Autowired
    private ApiAddressRepository apiAddressRepository;
    // ApiAddressRepository 주입

    // 2021.05.18 화 RestTemplate 구성중
    @PostMapping
    public Object findData(@RequestBody String addressData) {
        //
        //  RestTemplate TestCode 진행중
//        Object[] sdjskdjs = urlCode.postURLData(addressData);
//        urlCode.restTemplate(urlCode.makeURL(sdjskdjs), String.class);
        //
        //

        Object[] postData = urlCode.postURLData(addressData);   // Post방식 Data로 url호출에 필요한 객체 Data load
        String sigunguCd  = (String) postData[0];               // postData[0] = 시군구 코드
        String bjdongCd = (String) postData[1];                 // postData[1] = 법정동 코드
        Object object = apiAddressRepository.findBySigunguCdAndBjdongCd(sigunguCd, bjdongCd);
        // 해당 시군구코드, 법정동 코드에 해당하는 Data를 불러옴

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        String pageData = gson.toJson(object);  // Data를 Json형식으로 깔끔하게 파싱해서 String에 저장
        System.out.println(pageData);
        return "Data Load 완료";
    }
}

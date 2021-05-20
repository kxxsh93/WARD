package com.project.wefund.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.wefund.repository.ApiAddressRepository;
import com.project.wefund.util.URLCode;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "findApiAddress", method = {RequestMethod.POST})
public class SelectApiAddressController_v3 {
    @Autowired
    private URLCode urlCode;
    @Autowired
    private ApiAddressRepository apiAddressRepository;


    // 2021.05.18 화 RestTemplate 구성중
    @PostMapping
    public Object findData(@RequestBody String addressData) {

        //
        //  RestTemplate TestCode
//        Object[] sdjskdjs = urlCode.postURLData(addressData);
//        urlCode.restTemplate(urlCode.makeURL(sdjskdjs), String.class);
        //
        //


        Object[] postData = urlCode.postURLData(addressData);
        String sigunguCd  = (String) postData[0];
        String bjdongCd = (String) postData[1];
        Object object = apiAddressRepository.findBySigunguCdAndBjdongCd(sigunguCd, bjdongCd);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        String pageData = gson.toJson(object);
        System.out.println(pageData);
        return "Data Load 완료";
    }
}

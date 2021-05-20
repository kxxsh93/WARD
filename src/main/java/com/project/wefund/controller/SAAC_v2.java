//package com.project.wefund.controller;
//
//import com.project.wefund.util.URLCode;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(path = "/findAddress", method = {RequestMethod.POST})
//public class SAAC_v2 {
//
//    @Autowired
//    private URLCode urlCode;
//
//    @PostMapping
//    public String findCustomAddress(@RequestBody String addressData) {
////        Long numOfRows = (Long) urlCode.checkEtcCode(addressData)[0];
////        Long totalCnt = (Long) urlCode.checkEtcCode(addressData)[1];
////        Long pageCnt = (Long) urlCode.checkEtcCode(addressData)[2];
//
//        String url = urlCode.makeURL(urlCode.postURLData(addressData));
//        JSONObject cleanData = urlCode.preprocessingData(url);
////        JSONArray example = urlCode.printAddressData(cleanData);
////
//////        System.out.println(numData);
////        Gson gson = new GsonBuilder()
////                .setPrettyPrinting()
////                .disableHtmlEscaping()
////                .create();
////        String addd = gson.toJson(example);
////        System.out.println(example.size());
////        System.out.println(totalData.size());
//
////        for (int i = 1; i <= loopCnt; i++) {
////            String url = urlCode.makeURL(urlCode.postURLData(addressData), i);
////            JSONObject cleanData = urlCode.preprocessingData(url);
////            numData = urlCode.printAddressData(cleanData);
////            System.out.println("numData");
////            System.out.println(numData);
////
//////            String pureData = numData.substring(1, numData.length() - 1);
//////
//////            System.out.println("pureData");
//////            System.out.println(pureData);
////            totalData.append(numData);
////            System.out.println("totalData");
////            System.out.println(totalData);
////
////        }
////        Gson gson = new GsonBuilder()
////                .setPrettyPrinting()
////                .disableHtmlEscaping()
////                .create();
////        System.out.println(gson.toJson(totalData));
//////        System.out.println(totalData.size());
//
//
//
//
//
//
//
//
//        // url 완성
////        try {
////            // Post Data 잘들어오나 Test
//////            Object[] aaa = urlCode.postURLData(addressData);
////            for (int i = 0; i < aaa.length; i++) {
////                System.out.println(aaa[i]);
////            }
////
//////            String url = urlCode.makePostURL(addressData);
//////            String rawData = urlCode.findCode(url);
//////            sss = urlCode.printAddressData(rawData, false);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        return "addd";
//    }
//}

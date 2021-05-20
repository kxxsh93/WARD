//package com.project.wefund.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.project.wefund.repository.ApiAddressRepository;
//import com.project.wefund.vo.ApiAddress;
//import lombok.SneakyThrows;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping(value = "/findAddress_pastversion", method = {RequestMethod.GET})
//public class SAAC_v1 {
//
//    @SneakyThrows
//    @PostMapping
//    public String findAddressHttp(@RequestBody String addressData) {
////
////        URLCode urlCode = new URLCode();
////
////        String sadddd= urlCode.findCode(addressData);
//        StringBuffer result = new StringBuffer();
//        ObjectMapper mapper = new ObjectMapper();
//
//        StringBuffer findData = new StringBuffer();
//
//        HashMap<String, String> map;
//        map = mapper.readValue(addressData, new TypeReference<>() {
//        });
//
//        // 입력받을 url Data
//        String sigunguCd = map.get("sigunguCd");
//        String bjdongCd = map.get("bjdongCd");
//        String platGbCd = map.get("platGbCd");
//        String bun = map.get("bun");
//        String ji = map.get("ji");
//        Long numOfRows = Long.valueOf(map.get("numOfRows"));
//        Long pageNo = Long.valueOf(map.get("pageNo"));
//
//
//        try {
//            String urlstr = "http://apis.data.go.kr/1613000/BldRgstService_v2/" +
//                    "getBrTitleInfo?serviceKey=" +
//                    "3JvEO9phpffLtXWHbQWQJS2c34O7P%2FCKKHjx%2FRBeMhzPINmIaqkGML" +
//                    "kHgFqub90Cmx9y6ZqfL4ANFmnavvdQqw%3D%3D" +
//                    "&sigunguCd=" + sigunguCd +        // 시군구코드 11680
//                    "&bjdongCd="  + bjdongCd +         // 법정동코드 10300
//                    "&platGbCd="  + platGbCd +         // platGbCd -> 0: 대지, 1: 산, 2: 블록
//                    "&bun="       + bun +              // 번 0012
//                    "&ji="        + ji +               // 지 0000
//                    "&numOfRows=" + numOfRows +        // 페이지당 목록 수 10
//                    "&pageNo="    + pageNo +           // 페이지번호 1
//                    "&_type=json";
//            URL url = new URL(urlstr);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
//
//            String returnLine;
//
//            while ((returnLine = br.readLine()) != null) {
//                result.append(returnLine);
//            }
//            urlConnection.disconnect();
//
//            // Raw Json data 전처리
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(result));
//            JSONObject parse_response = (JSONObject) jsonObject.get("response");
//            JSONObject parse_body = (JSONObject) parse_response.get("body");
//            JSONObject parse_items = (JSONObject) parse_body.get("items");
//
//            Long totalCnt = (Long) parse_body.get("totalCount");
//            Gson gson = new GsonBuilder()
//                    .setPrettyPrinting()
//                    .disableHtmlEscaping()
//                    .create();
////            Gson gson = new GsonBuilder().serializeNulls().create();
//            try {
//                if (totalCnt == 1 || numOfRows == 1 || ((totalCnt % numOfRows == 1)
//                        && totalCnt == (numOfRows * (pageNo - 1)) + 1)) {
//                    JSONObject apiJson = (JSONObject) parse_items.get("item");
//////                    ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
//////                    System.out.println(apiAddress);
//////                    apiAddressRepository.save(apiAddress);
//                    System.out.println("저장완료. DB를 확인해주세요.");
//                    Thread.sleep(3000);
//
//                } else if (totalCnt > 1) {
//                    JSONArray parse_item = (JSONArray) parse_items.get("item");
//                    for (int j = 0; j < parse_item.size(); j++) {
//                        JSONObject apiJson = (JSONObject) parse_item.get(j);
//                        String jsonOutput = gson.toJson(apiJson);
//                        System.out.println(apiJson);
//                        findData.append(jsonOutput);
//////                        ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
//////                        apiAddressRepository.save(apiAddress);
//////                        Thread.sleep(50);
//                    }
//                    if (totalCnt > (numOfRows * pageNo) && totalCnt > numOfRows) {
//                        pageNo += 1;
//////                        PageNo = String.valueOf(PageNo);
//                        System.out.println(totalCnt);
//                        System.out.println(pageNo);
//                        Thread.sleep(100);
//                    } else {
//                        System.out.println("totalCnt가 numOfRows보다 작아서 while문 탈출");
//                        Thread.sleep(3000);
//                    }
//                }
//            } catch (ClassCastException c) {
//                System.out.println("값이 없음.");
//                Thread.sleep(3000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        return findData.toString();
//        return "";
//
//
//    }
//}

//package com.project.wefund.controller;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.project.wefund.repository.AddressCodeRepository;
//import com.project.wefund.repository.ApiAddressRepository;
//import com.project.wefund.vo.AddressCode;
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
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.function.Predicate;
//
//@RestController
////@GetMapping(path = "/apitest") // 같은 어노테이션( getmapping이 더 최신)
//@RequestMapping(value = "/api", method = {RequestMethod.GET, RequestMethod.POST})
//public class SAAAC_v3 {
////    @JsonInclude(JsonInclude.Include.NON_NULL)  // <- null값 안받는 어노테이션(적용되는지는 모름)
//
//    @Autowired
//    private ApiAddressRepository apiAddressRepository;
//
//    @Autowired
//    private AddressCodeRepository addressCodeRepository;
//
//
////    public List<String> getTotalCodes() {   // totalCode(sigunguCd + bjdongCd) 가져오는 method
////        List<String> codes = addressCodeRepository.getTotalCode();
////        return codes;
////    }
//
//    @SneakyThrows
//    @PostMapping
//    public String callApiHttp(@RequestBody String apiData) {
//        StringBuffer result = new StringBuffer();
//        ObjectMapper mapper = new ObjectMapper();
//
////        ArrayList codeList = (ArrayList) getTotalCodes(); //   지금 안쓰이는 Code
//
//        // codeSavePoint가 null인 값 모두 가져오기
//        List<AddressCode> addressCodeList = addressCodeRepository.findAllByCodeSavePoint(false);
//
//
//        //
//        // 이사님 추천 코드, 이대로 진행
//        //
//        int cnt = 0;
//        for (AddressCode addressCode:addressCodeList){
//            cnt += 1;
//
//
//            String testcode = addressCode.getTotalCode();
//
//            String Si = testcode.substring(0, 5);
//            String Bj = testcode.substring(5);
//            String Pl = "";                  // null값으로 전체값 조회
//            String Bu = "";                  // null값으로 전체값 조회
//            String Ji = "";                  // null값으로 전체값 조회
//            String Nu = "1000";               // 100 일단 default
//            String Pa = "";
//
//            Boolean dataExist = apiAddressRepository.existsByBjdongCd(Bj);
//            System.out.println(dataExist);
//            if (dataExist = true) {
//                apiAddressRepository.deleteData(Si, Bj);
//                Thread.sleep(2000);
//
////                continue;
//            }
////            System.out.println(apiAddressRepository.existsByBjdongCd(Bj));
//
//            while (true) {
//                result.delete(0, result.length());  // StringBuffer 초기화
//                //
//                //  건축물대장 표제부 조회
//                //
//                //            System.out.println("위에 있는 Pa");
//                //            System.out.println(Pa);
//                Long totalCnt;
//                Long pageCnt;
//                Long Cnt;
//
//
//                try {
//                    String urlstr = "http://apis.data.go.kr/1613000/BldRgstService_v2/" +
//                            "getBrTitleInfo?serviceKey=" +
//                            "3JvEO9phpffLtXWHbQWQJS2c34O7P%2FCKKHjx%2FRBeMhzPINmIaqkGML" +
//                            "kHgFqub90Cmx9y6ZqfL4ANFmnavvdQqw%3D%3D" +
//                            "&sigunguCd=" + Si +        // 시군구코드 11680
//                            "&bjdongCd=" + Bj +         // 법정동코드 10300
//                            "&platGbCd=" + Pl +         // platGbCd -> 0: 대지, 1: 산, 2: 블록
//                            "&bun=" + Bu +              // 번 0012
//                            "&ji=" + Ji +               // 지 0000
//                            "&numOfRows=" + Nu +        // 페이지당 목록 수 10
//                            "&pageNo=" + Pa +           // 페이지번호 1
//                            "&_type=json";
//                    URL url = new URL(urlstr);
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("GET");
//
//                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
//
//                    String returnLine = "";
//                    //                System.out.println(returnLine);
//                    while ((returnLine = br.readLine()) != null) {
//                        result.append(returnLine);
//                    }
//                    urlConnection.disconnect();
//                    // Parser 생성
//
//
//
//                    JSONParser parser = new JSONParser();
//
//                    JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(result));
//                    JSONObject parse_response = (JSONObject) jsonObject.get("response");
//                    JSONObject parse_body = (JSONObject) parse_response.get("body");
//
//                    try {
//                        JSONObject parse_items = (JSONObject) parse_body.get("items");
//                        // totalCount,numOfRows  추출 -> 값에 따라 달라지는 형태 구별 및 값이 없을 경우 예외처리
//                        totalCnt = (Long) parse_body.get("totalCount");
//                        Cnt = (Long) parse_body.get("numOfRows");
//                        pageCnt = (Long) parse_body.get("pageNo");
//
//                        Gson gson = new GsonBuilder().serializeNulls().create();
//
//
//
//                        // data값이 1일 경우
//                        if (totalCnt == 1 || Cnt == 1 || ((totalCnt % Cnt == 1) && totalCnt == (Cnt * (pageCnt - 1)) + 1)) {
//                            JSONObject apiJson = (JSONObject) parse_items.get("item");
//                            ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
////                            System.out.println(apiAddress);sdjsaklddjkdjasldsa
//                            apiAddressRepository.save(apiAddress);
//                            System.out.println("저장완료. DB를 확인해주세요.");
//                            Thread.sleep(1500);
//                            break;
//
//                        // 그 외
//                        } else if (totalCnt > 1) {
//                            JSONArray parse_item = (JSONArray) parse_items.get("item");
//                            for (int j = 0; j < parse_item.size(); j++) {
//                                JSONObject apiJson = (JSONObject) parse_item.get(j);
//                                ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
////                                System.out.println(apiAddress);sdjsaklddjkdjasldsa
//                                apiAddressRepository.save(apiAddress);
////                                Thread.sleep(50);
//                            }
//                            // totalCnt > numOfRows 일 때.
//                            if (totalCnt > (Cnt * pageCnt) && totalCnt > Cnt) {
//                                pageCnt += 1;
//                                Pa = String.valueOf(pageCnt);
//                                //                            System.out.println("통과");
//                                //                            System.out.println("totalCnt값"s);
////                                System.out.println(totalCnt);
//                                //                            System.out.println("pageCnt값");
////                                System.out.println(pageCnt);
////                                Thread.sleep(100);
//                                //                            System.out.println("Cnt값");
////                                System.out.println(Cnt);
//                                //                            System.out.println("Pa값");
////                                System.out.println(Pa);
//                            } else {
////                                System.out.println("totalCnt가 numOfRows보다 작아서 while문 탈출");
//
//                                Thread.sleep(1500);
//                                break;
//                            }
//                        }
//                    } catch (ClassCastException c) {
//                        System.out.println("값이 없음.");
//                        Thread.sleep(1500);
//                        break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            addressCodeRepository.updateCodeSavePoint(Si, Bj);
//
//        }
//
//        System.out.println(cnt);
//
//        //
//        // 돌아가는 코드 - 수정금지
//        //
////        for (int i = 292; i < 300; i++) {
//////        for (int i = 141; i < codeList.size(); i++) {
//////            System.out.println(codeList.get(i));
////            Object TotalCode = codeList.get(i);
////
////
////            String code = TotalCode.toString();
////
////            String Si = code.substring(0, 5);
////            String Bj = code.substring(5);
////            //
////            // post시, 값 받을 때 필요함
////            //
////            HashMap<String, String> map = new HashMap<String, String>();
////            map = mapper.readValue(apiData, new TypeReference<HashMap<String, String>>() {
////            });
////
////            //        System.out.println(getTotalCodes());
////            //        for (int i = 0; i < getTotalCodes().size(); i++) {
////            //            System.out.println(getTotalCodes().get(i));
////            //        }
////
//////            String Si =  map.get("sigunguCd");
//////            String Bj =  map.get("bjdongCd");
////            String Pl = "";                  // null값으로 전체값 조회
////            String Bu = "";                  // null값으로 전체값 조회
////            String Ji = "";                  // null값으로 전체값 조회
////            //        String Pl =  map.get("platGbCd");
////            //        String Bu =  map.get("bun");
////            //        String Ji =  map.get("ji");
//////            String Nu = map.get("numOfRows");
////            String Nu = "100";
////            String Pa = "39";
//////            String Pa =  map.get("pageNo");
////
////
////            while (true) {
////                result.delete(0, result.length());  // StringBuffer 초기화
////                //
////                //  건축물대장 표제부 조회
////                //
////                //            System.out.println("위에 있는 Pa");
////                //            System.out.println(Pa);
////                Long totalCnt;
////                Long pageCnt;
////                Long Cnt;
////
////
////                try {
////                    String urlstr = "http://apis.data.go.kr/1613000/BldRgstService_v2/" +
////                            "getBrTitleInfo?serviceKey=" +
////                            "3JvEO9phpffLtXWHbQWQJS2c34O7P%2FCKKHjx%2FRBeMhzPINmIaqkGML" +
////                            "kHgFqub90Cmx9y6ZqfL4ANFmnavvdQqw%3D%3D" +
////                            "&sigunguCd=" + Si +        // 시군구코드 11680
////                            "&bjdongCd=" + Bj +         // 법정동코드 10300
////                            "&platGbCd=" + Pl +         // platGbCd -> 0: 대지, 1: 산, 2: 블록
////                            "&bun=" + Bu +              // 번 0012
////                            "&ji=" + Ji +               // 지 0000
////                            "&numOfRows=" + Nu +        // 페이지당 목록 수 10
////                            "&pageNo=" + Pa +           // 페이지번호 1
////                            "&_type=json";
////                    URL url = new URL(urlstr);
////                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
////                    urlConnection.setRequestMethod("GET");
////
////                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
////
////                    String returnLine = "";
////                    //                System.out.println(returnLine);
////                    while ((returnLine = br.readLine()) != null) {
////                        result.append(returnLine);
////                    }
////                    urlConnection.disconnect();
////                    // Parser 생성
////
////                    System.out.println(url);
////
////                    JSONParser parser = new JSONParser();
////
////                    JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(result));
////
////                    JSONObject parse_response = (JSONObject) jsonObject.get("response");
////                    JSONObject parse_body = (JSONObject) parse_response.get("body");
////
////                    try {
////                        JSONObject parse_items = (JSONObject) parse_body.get("items");
////                        // totalCount,numOfRows  추출 -> 값에 따라 달라지는 형태 구별 및 값이 없을 경우 예외처리
////                        totalCnt = (Long) parse_body.get("totalCount");
////                        Cnt = (Long) parse_body.get("numOfRows");
////                        pageCnt = (Long) parse_body.get("pageNo");
////
////                        Gson gson = new GsonBuilder().serializeNulls().create();
////
////
////                        if (totalCnt == 1 || Cnt == 1 || ((totalCnt % Cnt == 1) && totalCnt == (Cnt * (pageCnt - 1)) + 1)) {
////                            JSONObject apiJson = (JSONObject) parse_items.get("item");
////                            ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
////                            apiAddressRepository.save(apiAddress);
////                            System.out.println("저장완료. DB를 확인해주세요.");
////                            Thread.sleep(3000);
////                            break;
////
////                        } else if (totalCnt > 1) {
////                            JSONArray parse_item = (JSONArray) parse_items.get("item");
////                            for (int j = 0; j < parse_item.size(); j++) {
////                                JSONObject apiJson = (JSONObject) parse_item.get(j);
////                                ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
////                                apiAddressRepository.save(apiAddress);
////                                Thread.sleep(50);
////                            }
////                            if (totalCnt > (Cnt * pageCnt) && totalCnt > Cnt) {
////                                pageCnt += 1;
////                                Pa = String.valueOf(pageCnt);
////                                //                            System.out.println("통과");
////                                //                            System.out.println("totalCnt값"s);
////                                System.out.println(totalCnt);
////                                //                            System.out.println("pageCnt값");
////                                System.out.println(pageCnt);
////                                Thread.sleep(100);
////                                //                            System.out.println("Cnt값");
//////                                System.out.println(Cnt);
////                                //                            System.out.println("Pa값");
//////                                System.out.println(Pa);
////                            } else {
////                                System.out.println("totalCnt가 numOfRows보다 작아서 while문 탈출");
////                                Thread.sleep(3000);
////                                break;
////                            }
////                        }
////                    } catch (ClassCastException c) {
////                        System.out.println("값이 없음.");
////                        Thread.sleep(3000);
////                        break;
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        }
//        return "ok";
//    }
//
//}

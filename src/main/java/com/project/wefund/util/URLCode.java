package com.project.wefund.util;

import org.apache.http.client.HttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.wefund.repository.AddressCodeRepository;
import com.project.wefund.repository.ApiAddressRepository;
import com.project.wefund.vo.ApiAddress;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor    // IO 방법 다른방법 !
public class URLCode {

    private final ApiAddressRepository apiAddressRepository;

    private final AddressCodeRepository addressCodeRepository;

    // Trash Code
//    @Autowired
//    UrlCode(ApiAddressRepository apiAddressRepository){
//        this.
//    }
    //Trash Code

    @SneakyThrows
    public Object[] postURLData(String addressData) {   // Post로 전달받은 Data에서 url Code 가져오는 Method
        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, String> map;
        map = mapper.readValue(addressData, new TypeReference<>() {});

        String sigunguCd = map.get("sigunguCd");
        String bjdongCd  = map.get("bjdongCd");
        String platGbCd  = map.get("platGbCd");
        String bun       = map.get("bun");
        String ji        = map.get("ji");
        Long numOfRows   = Long.valueOf(map.get("numOfRows"));
        Long pageNo      = 1L;
        return new Object[] {sigunguCd, bjdongCd, platGbCd, bun, ji, numOfRows, pageNo};
    }

    @SneakyThrows
    public Object[] defaultURLDataForSaveAllData(String testCode, Long numOfRows) { // 전체 건축물대장 표제부를 조회하기 위해 필요한 url Code return Method
        String sigunguCd = testCode.substring(0, 5);    // test
        String bjdongCd  = testCode.substring(5);
        String platGbCd  = "";                  // null값으로 전체값 조회
        String bun       = "";                  // null값으로 전체값 조회
        String ji        = "";                  // null값으로 전체값 조회
        Long pageNo      = 1L;

        return new Object[] {sigunguCd, bjdongCd, platGbCd, bun, ji, numOfRows, pageNo};
    }

    public String makeURL(Object[] urlData) {   // 전달받은 url Code를 주입해 url 만들어주는 Method
        String urlstr = "";
        try{
            urlstr = "http://apis.data.go.kr/1613000/BldRgstService_v2/" +
                    "getBrTitleInfo?serviceKey=" +
                    "3JvEO9phpffLtXWHbQWQJS2c34O7P%2FCKKHjx%2FRBeMhzPINmIaqkGML" +
                    "kHgFqub90Cmx9y6ZqfL4ANFmnavvdQqw%3D%3D" +  // Example Data :
                    "&sigunguCd="   + urlData[0] +              //      시군구코드 11680
                    "&bjdongCd="    + urlData[1] +              //      법정동코드 10300
                    "&platGbCd="    + urlData[2] +              //      platGbCd -> 0: 대지, 1: 산, 2: 블록
                    "&bun="         + urlData[3] +              //      번 0012
                    "&ji="          + urlData[4] +              //      지 0000
                    "&numOfRows="   + urlData[5] +              //      페이지당 목록 수 10
                    "&pageNo="      + urlData[6] +              //      페이지번호 1
                    "&_type=json";
        } catch(Exception e){
            e.printStackTrace();
        }

        return urlstr;
    }

    @SneakyThrows
    public JSONObject preprocessingData(String urlstr) {        // url 호출로 건축물 표제부 Data를 받아서 전처리하는 Method
        StringBuffer result = new StringBuffer();
        URL url = new URL(urlstr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine);
        }
        urlConnection.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(result));
        JSONObject parse_response = (JSONObject) jsonObject.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");

        return parse_body;
    }

    @SneakyThrows
    public String printAddressData(JSONObject result, String sigunguCd, String bjdongCd) {  // Logic에 맞게 Data 전체 추출 후, 저장
        JSONObject parse_items = (JSONObject) result.get("items");
        Long numOfRows         = (Long) result.get("numOfRows");
        Long pageNo            = (Long) result.get("pageNo");
        Long totalCnt          = (Long) result.get("totalCount");

//        Iterable<ApiAddress> test = apiAddressRepository.findAll();
//        for(ApiAddress t : test){
//            System.out.println(t);
//        }
        Boolean dataExist = apiAddressRepository.existsByBjdongCd(bjdongCd);
        System.out.println(dataExist);
        if (dataExist == true) {
            apiAddressRepository.deleteData(sigunguCd, bjdongCd);
            Thread.sleep(2000);
        }
//        JSONArray dataList  = new JSONArray();
        Gson gson = new GsonBuilder().serializeNulls().create();
        while (true) {
                // 값이 1개라 object으로 들어올 때
                if (totalCnt == 1 || numOfRows == 1 || ((totalCnt % numOfRows == 1) && totalCnt == (numOfRows * (pageNo - 1)) + 1)) {
                    JSONObject apiJson = (JSONObject) parse_items.get("item");
                    ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
                    apiAddressRepository.save(apiAddress);
                    System.out.println("저장완료. DB를 확인해주세요.");
                    Thread.sleep(1500);
                    break;

                    // 그 외
                } else if (totalCnt > 1) {
                    JSONArray parse_item = (JSONArray) parse_items.get("item");
                    for (int j = 0; j < parse_item.size(); j++) {
                        JSONObject apiJson = (JSONObject) parse_item.get(j);
                        ApiAddress apiAddress = gson.fromJson(apiJson.toString(), ApiAddress.class);
                        apiAddressRepository.save(apiAddress);
                    }
                    System.out.println("totalCnt끝까지 도달");
                    System.out.println("저장완료. DB를 확인해주세요.");
                    break;
                }

        }
        addressCodeRepository.updateCodeSavePoint(sigunguCd, bjdongCd);
        return sigunguCd;
    }



    public Long checkTotalCount(String testCode) {  // 먼저 url api 호출 후에 Data 전체 개수(=totalCnt)를 찾는 Method
        String url = makeURL(defaultURLDataForSaveAllData(testCode, 100L));
        JSONObject result = preprocessingData(url);

        Long totalCnt = (Long) result.get("totalCount");
        return totalCnt;
    }


    //
    // Url 받아오는 방식 RestTemplate로 진행중
    //
//    public void restTemplate(String urlstr, Class<?> addressData) {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setReadTimeout(5000); // 읽기시간초과, ms
//        factory.setConnectTimeout(3000); // 연결시간초과, ms
//        HttpClient httpClient = HttpClientBuilder.create()
//                .setMaxConnTotal(100) // connection pool 적용
//                .setMaxConnPerRoute(5) // connection pool 적용
//                .build();
//        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
//        RestTemplate restTemplate = new RestTemplate(factory);
//
//        Object obj = restTemplate.getForObject(urlstr, String.class);
////        restTemplate
//        System.out.println(obj);
//    }
//
//
//    public void restTemplate_v2(String urlstr) {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectTimeout(5000);
//        factory.setReadTimeout(5000);
//        RestTemplate restTemplate = new RestTemplate(factory);
//
//        HttpHeaders header = new HttpHeaders();
//        HttpEntity<?> entity = new HttpEntity<>(header);
//
//
//    }
}









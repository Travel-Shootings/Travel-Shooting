package com.sparta.travelshooting.post.service;


import com.sparta.travelshooting.post.dto.NaverAddressDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//@RequiredArgsConstructor
@Service
public class NaverApiService {

    private final RestTemplate restTemplate;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    public NaverApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    // 네이버 검색 서비스
    public List<NaverAddressDto> searchAddress(String query) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("display", 5)
                .queryParam("query", query)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", naverClientId) // postman에서 헤더의 Key 값에 넣는 값
                .header("X-Naver-Client-Secret", naverClientSecret) //  postman에서 헤더의 Key 값에 넣는 값
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return fromJSONtoItems(responseEntity.getBody());
    }

    public List<NaverAddressDto> fromJSONtoItems(String response) {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray items = jsonObject.getJSONArray("items"); // key값으로 item을 주면 value값 다 불러옴
        List<NaverAddressDto> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            NaverAddressDto itemDto = new NaverAddressDto((JSONObject) item);
            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }
}

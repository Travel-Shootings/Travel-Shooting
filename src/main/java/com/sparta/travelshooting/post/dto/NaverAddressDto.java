package com.sparta.travelshooting.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
@Getter
@NoArgsConstructor
public class NaverAddressDto {
    private String title;
    private String address;
    private String roadAddress;



    public NaverAddressDto (JSONObject jsonObject) {
        this.title = jsonObject.getString("title");
        this.address = jsonObject.getString("address");
        this.roadAddress = jsonObject.getString("roadAddress");
    }
}

package com.sparta.travelshooting.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class EditInfoRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z가-힣1-9])(?!.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).*$",
            message = "닉네임에 특수문자는 포함되면 안됩니다.")
    private String nickname;

    @NotBlank
    private String region;
}

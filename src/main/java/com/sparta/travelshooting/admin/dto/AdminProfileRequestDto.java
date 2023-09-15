package com.sparta.travelshooting.admin.dto;

import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminProfileRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z가-힣])(?!.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).*$",
            message = "닉네임에 특수문자는 포함되면 안됩니다.")
    private String nickname;


    private RoleEnum role;
}

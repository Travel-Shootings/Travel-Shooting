package com.sparta.travelshooting.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).*$",
            message = "비밀번호는 대소문자, 특수문자가 모두 포함되어야 합니다.")
    private String password;

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z가-힣])(?!.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).*$",
            message = "닉네임에 특수문자는 포함되면 안됩니다.")
    private String nickname;

    @NotBlank
    private String region;
}

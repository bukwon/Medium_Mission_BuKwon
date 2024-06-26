package com.example.mediumproject.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "ID는 필수입니다.")
    private String username;

    @NotEmpty(message = "PW는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "PW 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    private Boolean ROLE_PAID;
}
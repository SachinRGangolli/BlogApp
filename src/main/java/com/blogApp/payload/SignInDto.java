package com.blogApp.payload;

import lombok.Data;

@Data
public class SignInDto {
    private String userNameOrEmail;
    private String password;
}

package com.example.loginAPI;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by bench on 07/03/2017.
 */
@Builder
@Getter
@Setter
public class UserDto {

    private String pseudo;

    private String email;

    private String password;

    private String token;
}

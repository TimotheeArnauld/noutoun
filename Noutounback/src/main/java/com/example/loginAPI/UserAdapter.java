package com.example.loginAPI;




/**
 * Created by bench on 07/03/2017.
 */

/**
 * the password will be encoded in the service
 */
public class UserAdapter {

    public static UserDto toDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .pseudo(user.getPseudo())
                .password(user.getPassword())
                .token(user.getToken())
                .build();
    }

    public static User toUser(UserDto dto){
        return User.builder()
                .pseudo(dto.getPseudo())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .token(dto.getToken())
                .build();
    }
}

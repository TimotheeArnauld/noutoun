package com.example.loginAPI.Service;

import org.springframework.stereotype.Service;

/**
 * Created by bench on 08/03/2017.
 */
@Service
public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String pseudo, String password);
}

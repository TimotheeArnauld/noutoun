package com.example;

import com.example.loginAPI.*;
import com.example.loginAPI.Service.UserServiceController;
import com.example.loginAPI.Service.UserServices;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotNull;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by bench on 08/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@UserData
public class UserServiceControllerIT {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServices userServices;

    @Before
    public void init(){
        RestAssured.port = localServerPort;
    }

    @Test
    public void should_get_all_users(){
        given().log().all()
                .when().get("users")
                .then().log().all()
                .statusCode(200)
                .body("$", hasSize(4));
    }

    @Test
    public void should_create_user(){
        String token = userServices.createToken("Yous2", "youyou");
        User user = User.builder()
                .pseudo("Yous2")
                .email("yous2@yous.com")
                .password("youyou")
                .build();
        given().log().all()
                .contentType(JSON)
                .body(user)
                .when().post("users")
                .then().statusCode(200)
                .body("pseudo", equalTo("Yous2"))
                .body("email", equalTo("yous2@yous.com"))
                .body("token", equalTo(token));
    }
    @Test
    public void should_get_user_by_pseudo(){
        given().log().all()
                .when().get("users/{pseudo}", "second")
                .then().log().all()
                .statusCode(200)
                .body("email", equalTo("second@second.com"))
                .body("id", equalTo(2));
    }

    @Test
    public void should_verify_user(){
        given().log().all()
                .when().get("/users/verify?pseudo=Yousria&password=yousria1234")
                .then().log().all()
                .statusCode(200)
                .body("id", equalTo(4));
    }

    @Test
    public void should_get_error_wrong_username_or_password(){
        given().log().all()
                .when().get("/users/verify?pseudo=haha&password=hihi")
                .then().log().all()
                .statusCode(200)
                .body("pseudo", equalTo("erreur"));
    }

    @Test
    public void should_verify_user_2(){
        assertThat(userServices.verifyUser("Yousria", "yousria1234"))
                .isEqualTo(true);
        assertThat(userServices.verifyUser("haha", "hihi"))
                .isEqualTo(false);
    }

    @Test
    public void should_verify_token(){
        String token = userServices.createToken("Yousria", "yousria1234");
        assertThat(userServices.verifyToken(token))
                .isEqualTo(true);
    }

    @Test
    public void should_verify_token_2(){
        UserDto dto = UserDto.builder()
                .pseudo("Yousria")
                .password("yousria1234")
                .token(userServices.createToken("Yousria", "yousria1234"))
                .build();
        given().log().all()
                .contentType("application/json")
                .body(dto)
                .when().post("/users/token")
                .then().log().all()
                .statusCode(200)
                .contentType("text/plain")
                .body(containsString("true"));
    }





}

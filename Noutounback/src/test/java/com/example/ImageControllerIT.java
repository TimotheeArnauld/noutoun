package com.example;

import com.example.fileApi.ImageDTO;
import com.example.loginAPI.UserData;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Nicolas on 25/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@UserData
public class ImageControllerIT {
    @LocalServerPort
private int localServerPort;

    @Before
    public void initialize_data(){


        RestAssured.port=localServerPort;

    }




    @Test
    public void should_upload_file(){

            given().multiPart("fileUpload",new File("src/test/resources/image1.jpg")).log().all()
                    .when().post("/image/doUpload?album_name=albumtest&pseudo=first&title=title")
                    .then().statusCode(200).body("title",equalTo("title"));
    }

    @Test
    public void should_get_image(){
        given().log().all()
                .when().get("/image/getImage/{id}",1)
                .then().log().all()
                .statusCode(200).contentType(JSON)
                .body("title",equalTo("testimage"))
                .body("likescore",equalTo(5)).body("dislikescore",equalTo(6))
                .body("datas",equalTo(null)).body("commentEntityList",equalTo(null));
    }
    @Test
    public void should_get_random_image(){
        given().log().all()
                .when().get("/image/getRandomImage")
                .then().log().all()
                .statusCode(200).body("$",hasSize(5));
    }

    @Test
    public void should_updated_like(){
        given().log().all()
                .when().post("/image/likeImage/{id}",1)
                .then().log().all()
                .statusCode(200).body("likescore",equalTo(6));
    }
    @Test
    public void should_updated_dislike(){
        given().log().all()
                .when().post("/image/dislikeImage/{id}",1)
                .then().log().all()
                .statusCode(200).body("dislikescore",equalTo(7));

    }
    @Test
    public void should_updated_title(){
        given().log().all()
                .when().post("/image/UpdateTitleImage/{id}?title=newtitle",1)
                .then().log().all()
                .statusCode(200).body("title",equalTo("newtitle"));
    }
}

package com.example;

import com.example.fileApi.*;
import com.example.fileApi.services.AlbumService;
import com.example.fileApi.services.ImageService;
import com.example.loginAPI.User;
import com.example.loginAPI.UserData;
import com.example.post.CommentDTO;
import com.example.post.CommentRepositoryImpl;
import com.example.post.services.CommentService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by kokoghlanian on 11/04/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@UserData
public class CommentControllerTest {


    @LocalServerPort
    private int localServerPort;

    @Autowired
    CommentService commentService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;

    @Before
    public void init(){
        RestAssured.port = localServerPort;
    }

    @Test
    public void should_delete_comment(){
        given()
                .log().all()
                .when()
                .delete("/comments/delete/{idComment}",1)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void test(){

    }

    @Test
    public void should_insert_comment(){
        given()
                .log().all()
                .when()
                .post("/comments/{pseudo}/{imageid}/{comment}","first","1","messagetest")
                .then()
                .log().all()
                .statusCode(200).body("comment",equalTo("messagetest"));
    }


   @Test
    public void should_update_comment(){
        given()
                .log().all()
                .when()
                .post("/comments/update/{idComment}/{comment}","1","SALUTSALUT")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void should_get_comment_by_image_entity(){
        given()
                .log().all()
                .when()
                .get("/comments/image/{idImageEntity}",1)
                .then()
                .log().all()
                .statusCode(200)
                .body("$", hasSize(1));
    }

    @Test
    public void should_get_comment_by_user(){
        given()
                .log().all()
                .when()
                .get("/comments/user/{idUser}",4)
                .then()
                .log().all()
                .statusCode(200)
                .body("$", hasSize(1));
    }

}

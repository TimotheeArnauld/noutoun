package com.example;

import com.example.fileApi.*;
import com.example.fileApi.services.AlbumService;
import com.example.fileApi.services.AlbumServiceImpl;
import com.example.fileApi.services.ImageService;
import com.example.fileApi.services.ImageServiceImpl;
import com.example.loginAPI.Service.UserServices;
import com.example.loginAPI.User;
import com.example.loginAPI.UserAdapter;
import com.example.loginAPI.UserData;
import com.example.post.CommentAdapter;
import com.example.post.CommentDTO;
import com.example.post.CommentEntity;
import com.example.post.CommentRepositoryImpl;
import com.example.post.services.CommentService;
import com.example.post.services.CommentServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.example.loginAPI.Role.USER;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by kokoghlanian on 09/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CommentServiceImpl.class,AlbumServiceImpl.class, ImageServiceImpl.class})
@DataJpaTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ImageService imageService;

    ImageEntity imageEntity;
    User user;
    CommentDTO commentOne;
    CommentDTO commentTwo;
    CommentDTO commentThree;


    @Before
    public void initialize_data(){

        user = User.builder()
                .pseudo("pseudo")
                .email("mail@mail.fr")
                .password("monmp")
                .role(USER)
                .build();

        AlbumEntity albumEntity =
                AlbumAdapter.toAlbumEntity(albumService.insertAlbum("bonjour", user));

        imageEntity =
                ImageAdapter.toImageEntity(imageService.insertImage("coucou",
                        albumEntity,
                        null));

        commentOne =  commentService.insertComment("BONJOURBONJOUR",user,imageEntity);
        commentTwo =  commentService.insertComment("coucou",user,imageEntity);
        commentThree =  commentService.insertComment("salut",user,imageEntity);

    }

    @After
    public void delete(){
        commentService.deleteAll();
    }

    @Test
    public void should_insert_comment(){

        CommentDTO comment =  commentService.insertComment("kokokokokookokoko",user,imageEntity);
        assertThat(comment.getComment()).isEqualTo("kokokokokookokoko");
        commentService.deleteComment(CommentAdapter.toComment(comment));
    }

    @Test
    public void should_find_comment_by_image_entity(){

        assertThat(commentService.getCommentsByImageEntity(imageEntity.getId()).size()).isEqualTo(3);
    }

    @Test
    public void should_find_comment_by_user(){

        assertThat(commentService.getCommentByUser(user.getId()).size()).isEqualTo(3);
    }

    @Test
    public void should_find_comment_by_id(){

        assertThat(commentService.findById(commentOne.getId()).getComment()).isEqualTo("BONJOURBONJOUR");
    }

    @Test
    public void should_update_comment(){

        commentService.updateComment(commentOne.getId(),"SALUTTOI");
        assertThat(commentService.getCommentsByImageEntity(imageEntity.getId()).get(0).getComment()).isEqualTo("SALUTTOI");
    }

    @Test
    public void should_delete_comment(){

        assertThat(commentService.getCommentsByImageEntity(imageEntity.getId()).size()).isEqualTo(3);

        commentService.deleteComment(CommentAdapter.toComment(commentService.findById(commentThree.getId())));

        assertThat(commentService.getCommentsByImageEntity(imageEntity.getId()).size()).isEqualTo(2);

    }
}

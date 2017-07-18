package com.example;

import com.example.fileApi.*;
import com.example.fileApi.services.AlbumService;
import com.example.fileApi.services.AlbumServiceImpl;
import com.example.fileApi.services.ImageService;
import com.example.fileApi.services.ImageServiceImpl;
import com.example.loginAPI.Role;
import com.example.loginAPI.User;
import com.example.loginAPI.UserRepository;
import com.example.post.CommentAdapter;
import com.example.post.CommentDTO;
import com.example.post.CommentRepositoryImpl;
import com.example.post.services.CommentServiceImpl;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.loginAPI.Role.USER;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Nicolas_Travail on 11/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AlbumServiceImpl.class, ImageServiceImpl.class})
@DataJpaTest
public class ImageServiceTest {

    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    ImageServiceImpl imageService;
    @Autowired
    UserRepository userRepository;


    ImageEntity imageEntity;
    ImageEntity imageEntity2;
    ImageEntity imageEntity3;
    User user;
    AlbumEntity albumEntity;
    AlbumEntity albumEntity2;
    @Before
    public void initialize_data(){

        user = User.builder()
                .pseudo("pseudo")
                .email("mail@mail.fr")
                .password("monmp")
                .role(USER)
                .build();
        userRepository.save(user);
       albumEntity =
                AlbumAdapter.toAlbumEntity(albumService.insertAlbum("bonjour", user));
        albumEntity2 =
                AlbumAdapter.toAlbumEntity(albumService.insertAlbum("bonjour2", user));

        try {
            imageEntity =
                    ImageAdapter.toImageEntity(imageService.insertImage("image1",
                            albumEntity,
                            new MockMultipartFile("file 1",new FileInputStream("src/test/resources/image1.jpg")).getBytes()));
            imageEntity2 =
                    ImageAdapter.toImageEntity(imageService.insertImage("image2",
                            albumEntity,
                            new MockMultipartFile("file 2",new FileInputStream("src/test/resources/image2.jpg")).getBytes()));
            imageEntity3 =
                    ImageAdapter.toImageEntity(imageService.insertImage("image2",
                            albumEntity2,
                            new MockMultipartFile("file 2",new FileInputStream("src/test/resources/image2.jpg")).getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




   @Test

    public void should_return_image1_with_id(){
        ImageDTO imageDTOResult= imageService.findById(imageEntity.getId());
        assertThat(imageDTOResult.getId()).isEqualTo(imageEntity.getId());
        assertThat(imageDTOResult.getTitle()).isEqualTo(imageEntity.getTitle());
        assertThat(Arrays.equals( imageDTOResult.getDatas(),imageEntity.getDatas()));
    }
    @Test
    public void should_return_image1_with_title(){
        ImageDTO imageDTOResult= imageService.findByTitle(imageEntity2.getTitle());
        assertThat(imageDTOResult.getId()).isEqualTo(imageEntity2.getId());
        assertThat(imageDTOResult.getTitle()).isEqualTo(imageEntity2.getTitle());
        assertThat(Arrays.equals( imageDTOResult.getDatas(),imageEntity2.getDatas()));
    }
    @Test
    public void should_update_dislike(){
        imageService.updateDislike(imageEntity);
        ImageDTO imageDTO= imageService.findById(imageEntity.getId());
        assertThat(imageDTO.getDislikescore().equals(imageEntity.getDislikescore()+1));
    }
    @Test
    public void should_update_Like(){
        imageService.updateLike(imageEntity);
        ImageDTO imageDTO= imageService.findById(imageEntity.getId());
        assertThat(imageDTO.getLikescore().equals(imageEntity.getLikescore()+1));
    }
    @Test
    public void should_update_Title(){
        imageService.updateTitle(imageEntity2,"newtitle");
        ImageDTO imageDTO= imageService.findById(imageEntity2.getId());
        assertThat(imageDTO.getTitle().equals("newtitle"));
    }

    @Test
    public void should_return_the_two_image_by_album(){
        List<ImageDTO> imageDTOList =imageService.getByAlbum(albumEntity.getId());
        assertThat(imageDTOList.size()).isEqualTo(2);
    }



}

package com.example.fileApi.services;

import com.example.fileApi.*;
import com.example.loginAPI.Role;
import com.example.loginAPI.Service.UserServices;
import com.example.loginAPI.User;
import com.example.loginAPI.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.loginAPI.Role.USER;

/**
 * Created by Nicolas on 09/04/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;


    private final AlbumService albumService;


     private final UserServices userServices;
    @Autowired
    public ImageController(ImageService imageService, AlbumService albumService, UserServices userServices) {
        this.imageService = imageService;
        this.albumService = albumService;
        this.userServices = userServices;
    }



   @RequestMapping(value = "/fichier", method = RequestMethod.GET)
    public void showUploadForm(Model model) {
//       System.out.println(userServices.getUserByPseudo("b").getPseudo());
        AlbumEntity albumEntity=AlbumAdapter.toAlbumEntity(albumService.insertAlbum("a",userServices.getUserByPseudo("b")));
        albumService.insertAlbum("a",albumEntity.getUser());
        MultipartFile multipartFile;
        try {
           multipartFile = new MockMultipartFile("file 1",new FileInputStream("image.jpg"));

            handleFileUpload(multipartFile,"a","b","c");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public ImageDTO handleFileUpload(
                                   @RequestParam("fileUpload") MultipartFile fileUpload, @RequestParam("album_name") String album_name, @RequestParam("pseudo") String pseudo,@RequestParam("title") String title) throws Exception {


        return imageService.insertImage(title,AlbumAdapter.toAlbumEntity(albumService.findByTitleAndPseudo(album_name,pseudo)), fileUpload.getBytes());


    }
    @GetMapping(value = "/getImage/{id}")
    public ImageDTO getImage(@PathVariable("id") Long id){
        ImageDTO imageDTO=imageService.findById(id);
        System.out.println(imageDTO.getTitle());
        return imageDTO;
    }
    @GetMapping(value = "/getRandomImage")
    public List<ImageDTO> getImageRandom(){
        List<ImageDTO> result=imageService.getRandomImages();
        System.out.println(result.size());
        return result ;
    }
    @RequestMapping(value = "/likeImage/{id}", method = RequestMethod.POST)
    public ImageDTO likeImage(@PathVariable("id") Long id){
        ImageDTO imageDTO=imageService.findById(id);
        imageService.updateLike(ImageAdapter.toImageEntity(imageDTO));
        imageDTO=imageService.findById(id);
        return imageDTO;
    }
    @RequestMapping(value = "/dislikeImage/{id}", method = RequestMethod.POST)
    public ImageDTO dislikeImage(@PathVariable("id") Long id){
        ImageDTO imageDTO=imageService.findById(id);
        imageService.updateDislike(ImageAdapter.toImageEntity(imageDTO));
        imageDTO=imageService.findById(id);
        return imageDTO;
    }
    @RequestMapping(value = "/UpdateTitleImage/{id}", method = RequestMethod.POST)
    public ImageDTO updateTitleImage(@PathVariable("id") Long id,@RequestParam("title") String title){
        ImageDTO imageDTO=imageService.findById(id);
        System.out.println(imageService.updateTitle(ImageAdapter.toImageEntity(imageDTO),title));
        ImageDTO imageDTOres=imageService.findById(id);
        return imageDTOres;
    }

    @GetMapping(value="/ImagesAlbum/{id}")
    public List<ImageDTO> getAllImagesFromAlbum(@PathVariable("id") Long id){
        List<ImageDTO> result =imageService.getByAlbum(id);
        return result;
    }

}

package com.example.post;

import com.example.fileApi.ImageAdapter;
import com.example.fileApi.ImageEntity;
import com.example.fileApi.services.ImageService;
import com.example.loginAPI.Service.UserServices;
import com.example.loginAPI.User;
import com.example.loginAPI.UserAdapter;
import com.example.post.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kokoghlanian on 09/04/2017.
 */

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final UserServices userServices;
    public final CommentService commentService;
    public final ImageService imageService;

    @Autowired
    public CommentController(UserServices userServices, CommentService commentService, ImageService imageService) {
        this.userServices = userServices;
        this.commentService = commentService;
        this.imageService = imageService;
    }

    @PostMapping("/update/{idComment}/{comment}")
    public void updateComment(@PathVariable Long idComment,@PathVariable String comment){//,@RequestParam("token")//String token) {
        //if(userServices.verifyToken(token))
            commentService.updateComment(idComment,comment);
    }

    @GetMapping("/image/{idImageEntity}")
    public List<CommentDTO> getCommentsByImageEntity(@PathVariable Long idImageEntity){
        return commentService.getCommentsByImageEntity(idImageEntity);
    }

    @GetMapping("/user/{idUser}")
    public List<CommentDTO> getCommentsByUser(@PathVariable Long idUser){
        return commentService.getCommentByUser(idUser);
    }

    @GetMapping("/{idComment}")
    public CommentDTO getCommentsById(@PathVariable Long idComment){
        return commentService.findById(idComment);
    }

    
    @PostMapping("/{pseudo}/{imageid}/{comment}")
    public CommentDTO insertComment(@PathVariable String pseudo,@PathVariable String comment, @PathVariable Long imageid){//},@RequestParam("token")String token){
        //if(userServices.verifyToken(token)){
                User user = userServices.getUserByPseudo(pseudo);
                ImageEntity imageEntity = ImageAdapter.toImageEntity(imageService.findById(imageid));
                return commentService.insertComment(comment, user, imageEntity);
            //}
            //return null;
        }

    @DeleteMapping("/delete/{idComment}")
    public void deleteComment(@PathVariable Long idComment){ //,@RequestParam("token") String token){
        //if(userServices.verifyToken(token)) {
            CommentEntity commentEntity = CommentAdapter.toComment(commentService.findById(idComment));
            commentService.deleteComment(commentEntity);
       // }
    }

}

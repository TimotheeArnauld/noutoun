package com.example.post.services;

import com.example.loginAPI.User;
import com.example.post.CommentDTO;
import com.example.fileApi.ImageEntity;
import com.example.post.CommentEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by kokoghlanian on 07/03/2017.
 */
public interface CommentService {

    void updateComment(Long id, String comment);

    CommentDTO insertComment(String comment, User user , ImageEntity imageEntity);

    List<CommentDTO> getCommentsByImageEntity(Long imageEntityId);

    List<CommentDTO> getCommentByUser(Long imageEntityId);

    CommentDTO findById(Long idComment);

    void deleteComment(CommentEntity comment);

    void deleteAll();
}

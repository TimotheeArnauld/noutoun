package com.example.post.services;

import com.example.loginAPI.User;
import com.example.post.CommentAdapter;
import com.example.post.CommentDTO;
import com.example.post.CommentEntity;
import com.example.post.CommentRepositoryImpl;
import com.example.fileApi.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by kokoghlanian on 07/03/2017.
 */
@Service
public class CommentServiceImpl implements CommentService {


    final private CommentRepositoryImpl commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepositoryImpl commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void updateComment(Long id, String comment) {
       commentRepository.updateComment(comment,id);
    }

    @Override
    @Transactional
    public CommentDTO insertComment(String comment, User user, ImageEntity imageEntity){
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(comment)
                .image(imageEntity)
                .user(user)
                .build();
        commentRepository.save(commentEntity);

        return CommentAdapter.toCommentDTO(commentEntity);
    }




    @Override
    @Transactional
    public List<CommentDTO> getCommentsByImageEntity(Long imageEntityId){

        List<CommentEntity> commentList = commentRepository.getCommentsByImageEntity(imageEntityId);
        List<CommentDTO> commentListDto = new ArrayList<>();
        for(CommentEntity comment : commentList)
        {
            commentListDto.add(CommentAdapter.toCommentDTO(comment));
        }
        return commentListDto;
    }

    @Override
    @Transactional
    public List<CommentDTO> getCommentByUser(Long userId){

        List<CommentEntity> commentList = commentRepository.getCommentByUser(userId);
        List<CommentDTO> commentListDto = new ArrayList<>();
        for(CommentEntity comment : commentList)
        {
            commentListDto.add(CommentAdapter.toCommentDTO(comment));
        }
        return commentListDto;
    }

    @Override
    public CommentDTO findById(Long idComment) {
        Optional<CommentEntity> comment = commentRepository.findById(idComment);
        return CommentAdapter.toCommentDTO(comment.get());
    }

    @Override
    public void deleteComment(CommentEntity comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }

}

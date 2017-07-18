package com.example.fileApi.services;

import com.example.fileApi.AlbumDTO;
import com.example.fileApi.AlbumEntity;
import com.example.fileApi.ImageDTO;
import com.example.loginAPI.User;

import java.util.List;

/**
 * Created by Nicolas on 09/04/2017.
 */
public interface AlbumService {
    AlbumDTO insertAlbum(String title,User user);
    int updateTitle(AlbumEntity album, String title);
    AlbumDTO findById(Long id);
    AlbumDTO findByTitle(String title);
    AlbumDTO findByTitleAndPseudo(String title,String pseudo);
    List<AlbumDTO> findAllByUser(Long id);
    void deleteAlbum(AlbumEntity albumEntity);

}

package com.example.fileApi.services;

import com.example.fileApi.AlbumEntity;
import com.example.fileApi.ImageDTO;
import com.example.fileApi.ImageEntity;

import java.util.List;

/**
 * Created by Nicolas on 09/04/2017.
 */
public interface ImageService {
    int updateLike(ImageEntity imageEntity);
    ImageDTO insertImage(String title, AlbumEntity album, byte[] datas);
    int updateDislike(ImageEntity imageEntity);
    int updateTitle(ImageEntity imageEntity, String title);
    List<ImageDTO> getRandomImages();
    ImageDTO findById(Long id);
    ImageDTO findByTitle(String title);
    List<ImageDTO> getByAlbum(Long id);
}

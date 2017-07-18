package com.example.fileApi.services;

import com.example.fileApi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Nicolas on 09/04/2017.
 */

@Service
public class ImageServiceImpl implements ImageService {


    ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository=imageRepository;
    }

    @Override
    @Transactional
    public int updateLike(ImageEntity imageEntity) {
        if(imageEntity == null)
            throw new IllegalArgumentException();

        return imageRepository.updateLike(imageEntity.getLikescore()+1,imageEntity.getId());
    }

    @Override
    @Transactional
    public int updateDislike(ImageEntity imageEntity) {
        if(imageEntity == null)
            throw new IllegalArgumentException();

        return imageRepository.updateDislike(imageEntity.getDislikescore()+1,imageEntity.getId());
    }

    //Ajouter l'user pour l'update et la création
    @Override
    @Transactional
    public int updateTitle(ImageEntity imageEntity, String title) {
        if(imageEntity == null)
            throw new IllegalArgumentException();

       return imageRepository.updateTitle(title,imageEntity.getId());
    }

    @Override
    @Transactional
    public ImageDTO insertImage(String title, AlbumEntity album, byte[] datas){
        ImageEntity imageEntity = ImageEntity.builder()
                .likescore(0)
                .dislikescore(0)
                .title(title)
                .datas(datas)
                .album(album)
                .build();

        imageRepository.save(imageEntity);

//        imageRepository.flush();
        return ImageAdapter.toImageDTO(imageEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ImageDTO findById(Long id) {
        return ImageAdapter.toImageDTO(imageRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public ImageDTO findByTitle(String title) {
        return ImageAdapter.toImageDTO(imageRepository.findByTitle(title).get(0));
    }

    @Override
    public List<ImageDTO> getByAlbum(Long id) {
        return imageRepository.findByAlbum(id).stream()
                .map(ImageAdapter::toImageDTO)
                .collect(toList());
    }


    @Override
    public List<ImageDTO> getRandomImages() {

        return imageRepository.findRandom().stream()
                .map(ImageAdapter::toImageDTO)
                .collect(toList());
    }

}

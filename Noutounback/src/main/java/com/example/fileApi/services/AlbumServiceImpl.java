package com.example.fileApi.services;

import com.example.fileApi.*;
import com.example.loginAPI.User;
import com.example.loginAPI.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by Nicolas on 09/04/2017.
 */
@Service
public class AlbumServiceImpl implements AlbumService {


    AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    //Ajouter l'user pour l'update et la création
    @Override
    @Transactional
    public int updateTitle(AlbumEntity albumEntity, String title) {
        if(albumEntity == null)
            throw new IllegalArgumentException();

        return albumRepository.updateTitle(title,albumEntity.getId());
    }

    @Override
    @Transactional
    public AlbumDTO insertAlbum(String title,  User user){
        AlbumEntity albumEntity = AlbumEntity.builder()
                .title(title)
                .user(user)
                .build();
        albumRepository.save(albumEntity);
        return AlbumAdapter.toAlbumDTO(albumEntity);
    }

    @Override
    @Transactional()
    public AlbumDTO findById(Long id) {
        return AlbumAdapter.toAlbumDTO(albumRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumDTO findByTitle(String title) {
        return AlbumAdapter.toAlbumDTO(albumRepository.findByTitle(title).get());
    }



    @Override
    @Transactional(readOnly = true)
    public AlbumDTO findByTitleAndPseudo(String title, String pseudo) {
        Optional<AlbumEntity> albumEntityOptional =albumRepository.findByTitleAndPseudo(title,pseudo);
        if(albumEntityOptional.isPresent())
            return AlbumAdapter.toAlbumDTO(albumEntityOptional.get());
        return null;
    }

    @Override
    public List<AlbumDTO> findAllByUser(Long id) {
     //   System.out.println(pseudo);
        return  albumRepository.findAllByUser(id).stream()
                .map(AlbumAdapter::toAlbumDTO)
                .collect(toList());
    }

    @Override
    public void deleteAlbum(AlbumEntity albumEntity) {
        albumRepository.delete(albumEntity);
    }
}
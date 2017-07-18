package com.example.fileApi;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Sirac
 */

public class AlbumAdapter {
    public static AlbumDTO toAlbumDTO(AlbumEntity albumEntity){

        return AlbumDTO.builder()
                .id(albumEntity.getId())
                .title(albumEntity.getTitle())
                .imageEntityList(albumEntity.getImageEntityList())
                .user(albumEntity.getUser()).build();
    }

    public static AlbumEntity toAlbumEntity(AlbumDTO albumDto) {

        return AlbumEntity.builder()
                .id(albumDto.getId())
                .title(albumDto.getTitle())
                .imageEntityList(albumDto.getImageEntityList())
                .user(albumDto.getUser()).build();
    }

    public static List<AlbumDTO> listToAlbumDTO(List<AlbumEntity> albumEntities) {
        List<AlbumDTO> resultList = new ArrayList<>();
        for (AlbumEntity albumEntity : albumEntities)
            resultList.add(toAlbumDTO(albumEntity));
        return resultList;
    }
    public static List<AlbumEntity> listToAlbumEntity(List<AlbumDTO> albumDTOS) {
        List<AlbumEntity> resultList = new ArrayList<>();
        for (AlbumDTO albumDTO : albumDTOS)
            resultList.add(toAlbumEntity(albumDTO));
        return resultList;
    }
}

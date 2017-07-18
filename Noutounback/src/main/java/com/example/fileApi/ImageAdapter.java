package com.example.fileApi;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 09/04/2017.
 */
public class ImageAdapter {
    public static ImageDTO toImageDTO(ImageEntity imageEntity){

        return ImageDTO.builder()
                .album(imageEntity.getAlbum())
                .dislikescore(imageEntity.getDislikescore())
                .title(imageEntity.getTitle())
                .id(imageEntity.getId())
                //.commentEntityList(imageEntity.getCommentEntityList())
                .datas(imageEntity.getDatas())
                .likescore(imageEntity.getLikescore()).build();
    }

    public static ImageEntity toImageEntity(ImageDTO imageDto) {

        return ImageEntity.builder()
                .id(imageDto.getId())
                .album(imageDto.getAlbum())
                .dislikescore(imageDto.getDislikescore())
                .title(imageDto.getTitle())
                .likescore(imageDto.getLikescore())
                .datas(imageDto.getDatas()).build();
                //.commentEntityList(imageDto.getCommentEntityList()).build();
    }

    public static List<ImageDTO> toListImageDTO(List<ImageEntity> imageEntityList){
        List<ImageDTO> imageEntityDTOList
                = new ArrayList<>();

        for(ImageEntity imageEntity : imageEntityList)
            imageEntityDTOList.add(toImageDTO(imageEntity));

        return imageEntityDTOList;
    }
}

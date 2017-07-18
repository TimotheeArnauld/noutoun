package com.example.fileApi;

import com.example.post.CommentEntity;
import lombok.*;

import java.util.List;

/**
 * Created by Nicolas on 09/04/2017.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;

    private AlbumEntity album;

    private String title;

    private Integer likescore;

    private Integer dislikescore;

    private byte[] datas;

    private List<CommentEntity> commentEntityList;
}

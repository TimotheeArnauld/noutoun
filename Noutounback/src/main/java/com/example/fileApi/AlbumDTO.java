package com.example.fileApi;

import com.example.loginAPI.User;
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
public class AlbumDTO {

    private Long id;

    private String title;

    private User user;

    private List<ImageEntity> imageEntityList;
}

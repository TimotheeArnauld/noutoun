package com.example.post;


import com.example.fileApi.ImageEntity;
import com.example.loginAPI.User;
import lombok.*;

/**
 * Created by kokoghlanian on 06/03/2017.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private User user;
    private String comment;
    private ImageEntity image;
}

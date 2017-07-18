package com.example.post.social.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author timotheearnauld
 */
@Builder
@Getter
@Setter
public class FriendDTO {
    private Long user;
    private String pseudo;
}

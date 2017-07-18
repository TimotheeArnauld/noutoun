package com.example.post.social.friend;

/**
 * @author timotheearnauld
 */
public class FriendAdapter {
    public static FriendDTO NodeToDto(Friends friendsNode){
        return FriendDTO.builder()
                .user(friendsNode.user)
                .pseudo(friendsNode.pseudo)
                .build();
    }
}

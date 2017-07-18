package com.example.post.social.friend;

import com.google.common.collect.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * @author timotheearnauld
 */

@Service
public class FriendService {
    private FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    /*
     * User gestion
     */

    /**
     * @return list of all users' id
     */

    List<FriendDTO> getAllUsers(){
        return Streams.stream(friendRepository.getAllUsers()).map(FriendAdapter::NodeToDto).collect(toList());
    }

    /**
     * @param id_user
     * @return
     */
    public boolean doesUserExist(Long id_user){
        return friendRepository.getUser(id_user) != null;
    }

    /**
     * @param id_user
     * @return
     */
    public boolean addUser(Long id_user, String pseudo) {
        try{
            friendRepository.addNewUser(id_user, pseudo);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean removeUser(Long id_user){
        try {
            friendRepository.removeUser(id_user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Friend gestion
     */

    public boolean areFriends(Long id_user, Long id_friend){
        return (friendRepository.areFriends(id_user, id_friend)) != null;
    }

    List<FriendDTO> findFriendsForUser(Long iduser){
        return Streams.stream(friendRepository.findAllFriends(iduser)).map(FriendAdapter::NodeToDto).collect(toList());
    }

    public void addFriendForUser(Long iduser, Long idfriend){
        friendRepository.addNewFriend(iduser, idfriend);
    }

    public void removeFriendForUser(Long iduser, Long idfriend){
        friendRepository.deleteFriend(iduser, idfriend);
        friendRepository.deleteFriend(idfriend, iduser);
    }

    public void cleanDB(){
        friendRepository.emptyDB();
    }
}

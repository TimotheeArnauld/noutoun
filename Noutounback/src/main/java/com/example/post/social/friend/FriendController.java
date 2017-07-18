package com.example.post.social.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author timotheearnauld
 */

@CrossOrigin
@RestController
@RequestMapping("/friends/")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     *  User control functions
     *  You need {user_id} to run the API.
     *  Optional: {friend_id}
     */

    @GetMapping
    public List<FriendDTO> getAllUsers(){
        return friendService.getAllUsers();
    }

    @RequestMapping(value="user", method = RequestMethod.GET)
    public HashMap<String, String> userExists(@RequestParam("id_user") Long id_user){
        HashMap<String, String>result = new HashMap<>();

        if(friendService.doesUserExist(id_user)){
            result.put("result", "true");
            result.put("message", "User exists");
        }else{
            result.put("result", "false");
            result.put("message", "User does not exist.");
        }

        result.put("id", String.valueOf(id_user));
        return result;
    }

    @RequestMapping(value="addUser", method = RequestMethod.GET)
    public HashMap<String, String> addUser(@RequestParam("id_user")Long id_user, @RequestParam("pseudo") String pseudo){
        HashMap<String, String>result = new HashMap<>();
        if(!friendService.addUser(id_user, pseudo)){
            result.put("result", "false");
            result.put("message", "User already exists in Neo4jDatabase");
            return result;
        }

        if(friendService.doesUserExist(id_user)){
            result.put("result", "true");
            result.put("message", "User has been correctly added.");
        }else{
            result.put("result", "true");
            result.put("message", "An error occured, please retry.");
        }

        result.put("id_user", String.valueOf(id_user));
        return result;
    }

    @RequestMapping(value = "removeUser", method = RequestMethod.GET)
    public HashMap<String, String>removeUser(@RequestParam("id_user") Long id_user){
        HashMap<String, String>result = new HashMap<>();

        if(!friendService.removeUser(id_user)){
            result.put("result", "false");
            result.put("message", "An error occured, please retry.");
            return result;
        }

        if(friendService.doesUserExist(id_user)){
            result.put("result", String.valueOf(true));
            result.put("message", "User has been correctly removed.");
        }else{
            result.put("result", String.valueOf(true));
            result.put("message", "User has not been correctly removed.");
        }

        result.put("id_user", String.valueOf(id_user));
        return result;
    }

    /**
     *  Friend control functions
     *  You need {user_id} to run the API.
     *  Optional: {friend_id}
     */

    @RequestMapping(value="friends", method = RequestMethod.GET)
    public List<FriendDTO> getFriendsForUser(@RequestParam("id_user") Long id_user){
        return friendService.findFriendsForUser(id_user);
    }

    @RequestMapping(value="addFriend", method = RequestMethod.GET)
    public HashMap<String, String> addFriendForUser(@RequestParam("id_user") Long id_user, @RequestParam("id_friend") Long id_friend){
        friendService.addFriendForUser(id_user, id_friend);

        HashMap<String, String>result = new HashMap<>();

        if(friendService.areFriends(id_user, id_friend)){
            result.put("result", "true");
            result.put("message", "Relationship has been correctly added.");
        }else{
            result.put("result", "false");
            result.put("message", "An error occured, please retry.");
        }

        result.put("id_user", String.valueOf(id_user));
        result.put("id_friend", String.valueOf(id_friend));
        return result;
    }

    @RequestMapping(value="areTheyFriends", method = RequestMethod.GET)
    public HashMap<String, String>areTheyFriends(@RequestParam("id_user") Long id_user, @RequestParam("id_friend") Long id_friend){
        HashMap<String, String>result = new HashMap<>();
        if(!friendService.areFriends(id_user, id_friend) && !friendService.areFriends(id_friend, id_user)){
            result.put("result", "false");
        }else{
            result.put("result", "true");
        }

        return result;
    }

    @RequestMapping(value="removeFriend", method = RequestMethod.GET)
    public HashMap<String, String> removeFriendForUser(@RequestParam("id_user") Long id_user, @RequestParam("id_friend") Long id_friend){
        friendService.removeFriendForUser(id_user, id_friend);

        HashMap<String, String>result = new HashMap<>();
        if(!friendService.areFriends(id_user, id_friend)){
            result.put("result", "true");
            result.put("message", "Relationship has been correctly removed.");
        }else{
            result.put("result", "false");
            result.put("message", "An error occured, please retry.");
        }

        result.put("id_user", String.valueOf(id_user));
        result.put("id_friend", String.valueOf(id_friend));

        return result;
    }
}

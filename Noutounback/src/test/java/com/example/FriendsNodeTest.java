package com.example;

import com.example.post.social.friend.FriendRepository;
import com.example.post.social.friend.FriendService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author timotheearnauld
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendsNodeTest {
    @Autowired
    private
    FriendService friendService;

    @Autowired
    private
    FriendRepository friendRepository;

    @Before
    public void should_configure_test(){
        try {
            friendRepository.emptyDB();
            friendRepository.deleteAll();
            friendRepository.createDataBase();
            friendService.addUser(1L, "Test1");
            friendService.addUser(2L, "Test2");
            friendService.addUser(3L, "Test3");
            friendService.addUser(4L, "Test4");
            friendService.addUser(5L, "Test5");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_delete_user(){
        try{
            friendRepository.removeUser(5L);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void should_user_exist(){
        assertThat(friendService.doesUserExist(1L), is(true));
        assertThat(friendService.doesUserExist(2L), is(true));
        assertThat(friendService.doesUserExist(3L), is(true));
        assertThat(friendService.doesUserExist(4L), is(true));
        assertThat(friendService.doesUserExist(5L), is(true));
        assertThat(friendService.doesUserExist(6L), is(false));
    }

    @Test
    public void should_add_new_friend(){
        try{
            friendService.addFriendForUser(1L, 2L);
            friendService.addFriendForUser(2L, 3L);
            friendService.addFriendForUser(3L, 4L);
            friendService.addFriendForUser(1L, 4L);
            friendService.addFriendForUser(1L, 3L);
            friendService.addFriendForUser(2L, 4L);
            assertThat(friendService.areFriends(1L, 2L), is(true));
            assertThat(friendService.areFriends(2L, 3L), is(true));
            assertThat(friendService.areFriends(3L, 4L), is(true));
            assertThat(friendService.areFriends(1L, 4L), is(true));
            assertThat(friendService.areFriends(1L, 3L), is(true));
            assertThat(friendService.areFriends(2L, 4L), is(true));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void should_delete_friend(){
        try{
            friendService.removeFriendForUser(1L, 4L);
            assertThat(friendService.areFriends(1L, 4L), is(false));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void clean_database(){
        try{
            friendService.cleanDB();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
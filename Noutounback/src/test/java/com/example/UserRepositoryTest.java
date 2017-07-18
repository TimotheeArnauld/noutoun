package com.example;

import com.example.loginAPI.Role;
import com.example.loginAPI.User;
import com.example.loginAPI.UserData;
import com.example.loginAPI.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by bench on 07/03/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = UserRepository.class)
@UserData
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void should_find_by_username(){
        String pseudoToSearch = "first";
        Optional<User> user = userRepository.findByPseudo(pseudoToSearch);
        assertThat(user.get().getId()).isEqualTo(1);
    }

    @Test
    public void should_find_by_id(){
        Long idToSearch = 2L;
        Optional<User> user = userRepository.findById(idToSearch);
        assertThat(user.get().getPseudo()).isEqualTo("second");
    }

    @Test
    public void should_find_the_right_token(){
        String tokenExpected = "fifgnvoirg657HVT4";
        String tokenFound = userRepository.getToken("second");
        assertThat(tokenFound).isEqualTo(tokenExpected);
    }

    @Test
    public void should_find_the_right_username(){
        String pseudoExpected = "second";
        String pseudoFound = userRepository.getPseudo("fifgnvoirg657HVT4");
        assertThat(pseudoFound).isEqualTo(pseudoExpected);
    }

    @Test
    public void should_find_by_token(){
        String tokenToSearch = "fifgnvoirg657HVT4";
        Optional<User> user = userRepository.findByToken(tokenToSearch);
        assertThat(user.get().getPseudo()).isEqualTo("second");
    }

    @Test
    public void should_find_id_by_pseudo(){
        String pseudoToSearch = "second";
        Long id = Long.valueOf(userRepository.findIdByPseudo(pseudoToSearch));
        assertThat(id).isEqualTo(2L);
    }

    @Test
    public void should_find_id_by_pseudo_2(){
        User u = User.builder()
                .pseudo("fourth")
                .email("fourth@fourth.com")
                .password("fourth1234")
                .build();
        userRepository.save(u);
        Long id = Long.valueOf(userRepository.findIdByPseudo("fourth"));
        assertThat(id).isEqualTo(5L);
    }

}

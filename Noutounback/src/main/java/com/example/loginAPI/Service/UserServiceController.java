package com.example.loginAPI.Service;

import com.example.loginAPI.User;
import com.example.loginAPI.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bench on 08/03/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserServiceController {

    private final UserServices userServices;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceController(UserServices userServices, SecurityService securityService, UserValidator userValidator) {
        this.userServices = userServices;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userServices.getAllUsers();
    }

    @PostMapping
    public UserDto insertUser(@RequestBody User user){
        return userServices.createUser(user.getPseudo(), user.getEmail(), user.getPassword(), user.getRole());
    }
    @GetMapping("/{pseudo}")
    public User getUserByPseudo(@PathVariable String pseudo){
        User user = userServices.getUserByPseudo(pseudo);
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    @PostMapping("/token")
    public String verifyToken(@RequestBody User user){
        return String.valueOf(userServices.verifyToken(user.getToken()));
    }
    @GetMapping("/verify")
    public User verifyUser(@RequestParam("pseudo") String pseudo,
                             @RequestParam("password") String password){
        User error = User.builder()
                .pseudo("erreur")
                .build();
        if(userServices.verifyUser(pseudo, password) == true){
            User user = userServices.getUserByPseudo(pseudo);
            if(userServices.verifyToken(user.getToken()) == false){
                return error;
            }
            return User.builder()
                    .id(user.getId())
                    .token(user.getToken())
                    .build();
        }else{
            return error;
        }
    }

    @GetMapping("/delete")
    public void removeUser(@RequestParam("id") Long id){
        userServices.removeUser(id);
    }
}

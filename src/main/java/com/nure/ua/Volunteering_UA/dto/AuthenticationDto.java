package com.nure.ua.Volunteering_UA.dto;

import com.nure.ua.Volunteering_UA.model.user.User;
import lombok.Data;

@Data
public class AuthenticationDto {

    private String username;
    private String password;
    private String email;

    public User toUser(){
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}

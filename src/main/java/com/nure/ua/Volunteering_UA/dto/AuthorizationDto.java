package com.nure.ua.Volunteering_UA.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nure.ua.Volunteering_UA.model.user.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationDto {

    private String username;
    private String password;



    public User toUser(){
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        return user;
    }


}

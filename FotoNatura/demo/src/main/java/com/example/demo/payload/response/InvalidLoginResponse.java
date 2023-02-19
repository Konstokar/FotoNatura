package com.example.demo.payload.response;

import lombok.Getter;

@Getter // геттеры
public class InvalidLoginResponse {
    private String username;
    private String password;
    public InvalidLoginResponse(){
        this.username = "Invalid Username";
        this.username = "Invalid Password";
    }
}

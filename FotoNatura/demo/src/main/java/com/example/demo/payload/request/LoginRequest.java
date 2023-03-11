package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty; // "элемент с данной аннотацией не может быть пустым"

@Data
public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty") // message - что выводится, если элемент всё-таки пустой
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}

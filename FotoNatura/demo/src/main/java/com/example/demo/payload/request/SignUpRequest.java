package com.example.demo.payload.request;

import com.example.demo.annotations.PasswordMatches;
import com.example.demo.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email; // "строка должна соотвыетствовать формату электронной почты"
import javax.validation.constraints.NotBlank; // проверяет, что строка не пуста
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size; // "Размер аннотированного элемента должен находиться в пределах указанных
// границ (включено)"

@Data
@PasswordMatches
public class SignUpRequest {
    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required") //
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String firstname;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6) // min - минимальная длина строки
    private String password;
    private String confirmPassword;
}

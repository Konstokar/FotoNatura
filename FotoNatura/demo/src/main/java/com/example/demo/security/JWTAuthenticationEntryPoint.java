package com.example.demo.security;

import com.example.demo.payload.response.InvalidLoginResponse;
import com.google.gson.Gson; // Библиотека, позволяющая конвертировать объекты JSON в Java-объекты и наоборот
import org.springframework.http.HttpStatus; // Перечисление кодов состояния HTTP
import org.springframework.security.core.AuthenticationException; // Абстрактный суперкласс для всех исключений,
// связанных с Authentication недействительным объектом по какой-либо причине.
import org.springframework.security.web.AuthenticationEntryPoint; // Используется для ExceptionTranslationFilter
// запуска схемы аутентификации.
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse); // toJson() - конвертация объекта класса в JSON
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Взятие кода ошибки "Неавториозованно" (401)
        response.getWriter().println(jsonLoginResponse);
    }
}

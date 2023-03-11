package com.example.demo.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Обертка для ответа и дополнительно для HTTP заголовков и кода статуса
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils; // Различные служебные методы сбора
import org.springframework.validation.BindingResult; // Расширяет Errors для возможностей регистрации ошибок, позволяя
// Validator применять, а также добавляет специфичный для привязки анализ и построение модели.
import org.springframework.validation.FieldError; // Инкапсулирует ошибку поля, то есть причину отклонения конкретного
// значения поля.
import org.springframework.validation.ObjectError; // Инкапсулирует ошибку объекта, то есть глобальную причину
// отклонения объекта.

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponceErrorValidaton {
    public ResponseEntity<Object> mapValidationService(BindingResult result){
        if (result.hasErrors()) { // есть ли ошибки?
            Map<String, String> errorMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(result.getAllErrors())){ // .isEmpty(...) - Является ли предоставленная
                // Коллекция null или пустой,
                // .getAllErrors() - Получить все ошибки, как глобальные, так и полевые
                for (ObjectError error :
                        result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage()); // .getCode() - Возвращает код по
                    // умолчанию этого разрешимого объекта, то есть последний в массиве кодов
                    // .getDefaultMessage() - получение сообщения ошибки
                }
            }
            for (FieldError error:
                    result.getFieldErrors()) { // .getFieldErrors() - получение всех ошибок полей
                errorMap.put(error.getField(), error.getDefaultMessage()); // .getField() - получение поля
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

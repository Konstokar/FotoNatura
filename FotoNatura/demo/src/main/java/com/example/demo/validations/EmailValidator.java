package com.example.demo.validations;

import com.example.demo.annotations.ValidEmail;

import javax.validation.ConstraintValidator; // Определяет логику для проверки данного ограничения для данного типа
// объекта.
import javax.validation.ConstraintValidatorContext; // Предоставляет контекстные данные и операции при применении
// заданного средства проверки ограничений
import java.util.regex.Matcher; // Механизм, который выполняет операции сопоставления последовательности символов,
// интерпретируя Pattern
import java.util.regex.Pattern; // Скомпилированное представление регулярного выражения

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) { // Инициализирует валидатор при подготовке к
        // isValid-вызовам.
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) { // Реализует логику
        // проверки.
        return validateEmail(email);
    }

    private boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN); // Компилирует заданное регулярное выражение в шаблон.
        Matcher matcher = pattern.matcher(email); // Создает сопоставитель, который будет сопоставлять переданное
        // значение с шаблоном.
        return matcher.matches(); // Попытки сопоставить всю область с шаблоном.
    }
}

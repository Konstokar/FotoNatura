package com.example.demo.annotations;

import com.example.demo.validations.EmailValidator;

import javax.validation.Constraint; // Помечает аннотацию как ограничение проверки компонента
import javax.validation.Payload; // Тип полезной нагрузки, который можно прикрепить к данному объявлению ограничения
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) //Указывает контексты, в которых применим
// тип аннотации
/*
* ElementType.TYPE - класс, интерфейс (включая тип аннотации) или объявление перечисления
* ElementType.FIELD - Объявление поля (включает константы перечисления)
 * ElementType.ANNOTATION_TYPE - Объявление типа аннотации
 * */
@Retention(RetentionPolicy.RUNTIME) //
@Constraint(validatedBy = EmailValidator.class) // validatedBy - класс-ограничение
@Documented
public @interface ValidEmail {
    String message() default "Invalid email"; // реализация метода по умолчанию
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
}

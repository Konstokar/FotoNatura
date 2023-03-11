package com.example.demo.annotations;
import com.example.demo.validations.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented // Указывает, что аннотации с типом должны документироваться javadoc и подобными инструментами по умолчанию
public @interface PasswordMatches {
    String message() default "Password don't match";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
}

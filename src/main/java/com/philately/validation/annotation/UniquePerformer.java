package com.philately.validation.annotation;

import com.philately.validation.UniquePerformerValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePerformerValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePerformer {
    String message() default "Ship name already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

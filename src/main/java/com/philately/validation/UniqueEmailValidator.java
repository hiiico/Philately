package com.philately.validation;


import jakarta.validation.ConstraintValidatorContext;
import com.philately.service.UserServiceImpl;
import com.philately.validation.annotation.UniqueEmail;

import jakarta.validation.ConstraintValidator;
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserServiceImpl userService;

    public UniqueEmailValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findUserByEmail(value) == null;
    }


}
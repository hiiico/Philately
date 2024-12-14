package com.philately.validation;


import jakarta.validation.ConstraintValidator;
import com.philately.validation.annotation.UniquePerformer;

import jakarta.validation.ConstraintValidatorContext;

public class UniquePerformerValidator implements ConstraintValidator<UniquePerformer, String> {

//    private final PaintingServiceImpl songService;
//
//    public UniquePerformerValidator(PaintingServiceImpl songService) {
//        this.songService = songService;
//    }


    public UniquePerformerValidator() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
